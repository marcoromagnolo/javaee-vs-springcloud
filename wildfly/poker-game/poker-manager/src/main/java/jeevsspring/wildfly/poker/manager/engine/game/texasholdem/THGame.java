package jeevsspring.wildfly.poker.manager.engine.game.texasholdem;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.engine.game.Game;
import jeevsspring.wildfly.poker.manager.engine.game.GameTimer;
import jeevsspring.wildfly.poker.manager.engine.hand.HandAction;
import jeevsspring.wildfly.poker.manager.engine.player.Bets;
import jeevsspring.wildfly.poker.manager.engine.player.Player;
import jeevsspring.wildfly.poker.manager.engine.player.Orders;
import jeevsspring.wildfly.poker.manager.engine.player.Turns;
import jeevsspring.wildfly.poker.manager.engine.table.TableAction;
import jeevsspring.wildfly.poker.manager.util.IdGenerator;

import java.util.HashMap;
import java.util.Map;

public class THGame extends Game<THGameAction> {

    private int smallBlind;
    private int bigBlind;
    private RoundPhase roundPhase;
    private Bets bets;
    private Orders orders;
    private Turns turns;
    private Map<String, GameTimer> timers;

    public THGame(String tableId, TableSettings settings, BoClient boClient) {
       super(tableId, settings, boClient);
        orders = new Orders(getSeats());
        timers = new HashMap<>();
    }

    @Override
    public void action(HandAction action) {
        // On the first time when game is starting
        if (!isRunning() && getPlayers().size() >= 2) {

            // Wait some seconds before game running
            // waitStart();
            setRunning(true);

            // Set Random dealer
            orders.randomize();
        }

        // On hand starting
        if (!isHandRunning()) {

            // Generate handId
            setHandId(IdGenerator.newHandId());

            // Set Dealer
            orders.next();
            setDealer(orders.current());

            // Prepare Players turn for hand
            turns = new Turns(orders.getAll());

            // Set SmallBlind, BigBlind and First to play
            smallBlind = orders.get(0);
            bigBlind = orders.get(1);
            int turn;
            if (getPlayers().size() > 2) {
                turn = orders.get(2);
            } else {
                turn = orders.get(1);
            }
            turns.set(turn);

            // Set all instance var for next actions
            setHandRunning(true);

            // Add Game Action to Queue
            addGameAction(action.getHandId());
        }

        // On betting round start or change
        if (!isRoundRunning()) {
            switch (roundPhase) {

                case PREFLOP:
                    roundPhase = RoundPhase.FLOP;

                    // Discard one card
                    getCardDeck().getCard();

                    // Add 3 Community Cards
                    getCommunityCards().add(getCardDeck().getCard());
                    getCommunityCards().add(getCardDeck().getCard());
                    getCommunityCards().add(getCardDeck().getCard());
                    setRoundRunning(true);
                    break;
                case FLOP:
                    roundPhase = RoundPhase.RIVER;

                    // Discard one card
                    getCardDeck().getCard();

                    // Add 1 Community Card
                    getCommunityCards().add(getCardDeck().getCard());
                    setRoundRunning(true);
                    break;
                case TURN:
                    roundPhase = RoundPhase.RIVER;

                    // Discard one card
                    getCardDeck().getCard();

                    // Add 1 Community Card
                    getCommunityCards().add(getCardDeck().getCard());
                    setRoundRunning(true);
                    break;
                case RIVER:
                    roundPhase = RoundPhase.SHOWDOWN;
                    setRoundRunning(true);
                    break;
                case SHOWDOWN:
                    roundPhase = null;
                    setRoundRunning(false);
                    break;
                default:
                    roundPhase = RoundPhase.PREFLOP;

                    // Distribute 2  cards for every player from Small Blind to Dealer
                    for (int i = 0; i < getPlayers().size(); i++) {
                        int n = (i + smallBlind) % getPlayers().size();
                        String playerId = getSeats().get(n);
                        Player player = getPlayers().get(playerId);
                        player.getCards().add(getCardDeck().getCard());
                        player.getCards().add(getCardDeck().getCard());
                    }

                    // Prepare Players turns for round
                    setRoundRunning(true);
            }
        }

        // Play the Betting Round
        if (isRoundRunning()) {

            // Get PlayerId from current turn index
            int seat = orders.indexOf(turns.current());
            String playerId = getSeats().get(seat);

            // Start waiting timer for this playerId
            timers.put(playerId, new GameTimer(getActionTimeout()));

            // Check temp on first
            if (getTemp().containsKey(playerId) && getTemp().get(playerId).getHandId().equals(getHandId())) {
                HandAction tempAction = getTemp().remove(playerId);
                round(tempAction);
            }

            // go ahead with queue
            if (playerId.equals(action.getPlayerId()) && action.getHandId().equals(getHandId())) {
                round(action);
            } else {

                // Put Player in temp
                getTemp().put(action.getPlayerId(), action);
            }

            // Go next turn
            turns.next();
        }
    }

    private void round(HandAction action) {
        // If action is in time is played else if it is in timeout player fold
        if (action.getTime() + getActionTimeout() < System.currentTimeMillis()) {

            // Select action type
            switch (action.getActionType()) {

                case RAISE:
                    raise(action.getHandId(), action.getPlayerId(), action.getOption());
                    break;
                case BET:
                    bet(action.getHandId(), action.getPlayerId(), action.getOption());
                    break;
                case CALL:
                    call(action.getHandId(), action.getPlayerId());
                    break;
                case CHECK:
                    check(action.getHandId(), action.getPlayerId());
                    break;
                case FOLD:
                    fold(action.getHandId(), action.getPlayerId());
                    break;
            }
        } else {
            fold(action.getHandId(), action.getPlayerId());
        }
    }

    @Override
    public void action(TableAction action) {
        if (!isRunning()) {
            switch (action.getActionType()) {
                case BUY_IN:
                    buyin(action.getPlayerId(), action.getOption());
                    break;
                case BUY_OUT:
                    buyout(action.getPlayerId());
                    break;
                case SIT_IN:
                    sitin(action.getPlayerId(), action.getOption());
                    break;
                case SIT_OUT:
                    sitout(action.getPlayerId());
                    break;
            }
        }
    }

    private void addGameAction(String handId) {
        THGameAction gameAction = new THGameAction(handId);
        gameAction.setCommunityCards(getCommunityCards());
        gameAction.setPlayers(getPlayers());
        gameAction.setPots(getPots());
        gameAction.setSeats(getSeats().toArray());
        gameAction.setTurn(turns.current());
        gameAction.setVisitors(getVisitors());
        gameAction.setBigBlind(bigBlind);
        gameAction.setSmallBlind(smallBlind);
        gameAction.setRoundPhase(roundPhase);
        getQueue().offer(gameAction);
    }

    private void raise(String handId, String playerId, String amount) {
        switch (roundPhase) {
            case PREFLOP:
                break;
            case FLOP:
                break;
            case RIVER:
                break;
            case TURN:
                break;
            case SHOWDOWN:
                break;
        }
    }

    private void bet(String handId, String playerId, String amount) {

    }

    private void call(String handId, String playerId) {

    }

    private void check(String handId, String playerId) {

    }

    private void fold(String handId, String playerId) {
        int seat = getSeats().indexOf(playerId);
        Integer index = orders.get(seat);
        Integer turn = turns.indexOf(index);
        turns.remove(turn);
    }

    private void sitin(String playerId, String seat) {
        // Add player to Seats
        int s = Integer.parseInt(seat);
        getSeats().add(s ,playerId);

        // Retrieve Player and add to players
        Player player = getBoClient().getPlayer(playerId);
        player.setSeat(s);
        getPlayers().put(playerId, player);

        // Update Player Index
        orders.update(getSeats());
    }

    private void sitout(String playerId) {
        // remove Player from Seats
        for (int i =0; i < getSeats().size(); i++) {
            if (getSeats().get(i).equals(playerId)) {
                getSeats().remove(i);
            }
        }

        // Remove Player from players
        getPlayers().remove(playerId);

        // Prepare turns
        orders = new Orders(getSeats());
    }

    private void buyin(String playerId, String amount) {

    }

    private void buyout(String playerId) {

    }

    public enum RoundPhase {
        PREFLOP, FLOP, RIVER, TURN, SHOWDOWN
    }

}
