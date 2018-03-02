package jeevsspring.wildfly.poker.manager.engine.game.texasholdem;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.engine.game.Game;
import jeevsspring.wildfly.poker.manager.engine.game.GameException;
import jeevsspring.wildfly.poker.manager.engine.game.GameTimer;
import jeevsspring.wildfly.poker.manager.engine.hand.HandAction;
import jeevsspring.wildfly.poker.manager.engine.hand.Pot;
import jeevsspring.wildfly.poker.manager.engine.player.Bets;
import jeevsspring.wildfly.poker.manager.engine.player.Player;
import jeevsspring.wildfly.poker.manager.engine.player.Orders;
import jeevsspring.wildfly.poker.manager.engine.player.Turns;
import jeevsspring.wildfly.poker.manager.engine.table.TableAction;
import jeevsspring.wildfly.poker.manager.util.IdGenerator;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.Map;

public class THGame extends Game<THGameAction> {

    // Logger
    private final Logger logger = Logger.getLogger(getClass());

    // Seat of Small Blind
    private int smallBlind;

    // Seat Big Blind
    private int bigBlind;

    // Round Phase
    private RoundPhase roundPhase;

    // Player Bets
    private Bets bets;

    // Player Orders
    private Orders orders;

    // Player Turns
    private Turns turns;

    // Game Timers
    private GameTimer timer;

    /**
     * Constructor
     * @param tableId
     */
    public THGame(String tableId, TableSettings settings, BoClient boClient) {
       super(tableId, settings, boClient);

    }

    @Override
    public void onSettingsApply() {
        orders = new Orders(getSeats());
        timer = new GameTimer(getSettings().getActionTimeOut());
    }

    /**
     * Table Action pop from queue by TableId
     * Only during hand of game not running
     *  - buyin
     *  - buyout
     *  - sitin
     *  - sitout
     * @param action
     */
    @Override
    public void onAction(TableAction action) {
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

    /**
     * Play the hand actions pop from queue by TableId
     * @param actions
     */
    @Override
    public void onAction(Map<String, HandAction> actions) {
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

            // Set hand as running
            setHandRunning(true);

            // Add Game Action to Queue
            addGameAction();
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

                    // Update Pot
                    updatePots(bets);
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

                    // Transform pot to amount for each player
                    reward();
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

                    // Instantiate Pots
                    setPots(new ArrayList<>());
            }

            // Instantiate Bets for every round phase
            bets = new Bets();
        }

        // Play the Betting Round
        if (isRoundRunning()) {
            round(actions);
        }
    }

    /**
     * Round Phase
     */
    public enum RoundPhase {
        PREFLOP, FLOP, RIVER, TURN, SHOWDOWN
    }


    /**
     * Play the round
     * Wait for player turn and get actions from map
     * If player action is in time switch the actions otherwise force to fold
     * @param actions
     */
    private void round(Map<String, HandAction> actions) {

        // Get PlayerId from current turn index
        int seat = orders.indexOf(turns.current());
        String playerId = getSeats().get(seat);

        // Start waiting timer for this playerId
        if (timer.isOnTime(playerId)) {

            // Process round action or wait until timeout
            if (actions.containsKey(playerId)) {
                HandAction action = actions.get(playerId);
                if (action.getHandId().equals(getHandId())) {

                    // Select action type
                    try {
                        switch (action.getActionType()) {

                            case RAISE:
                                raise(action.getPlayerId(), action.getOption());
                                break;
                            case BET:
                                bet(action.getPlayerId(), action.getOption());
                                break;
                            case CALL:
                                call(action.getPlayerId());
                                break;
                            case CHECK:
                                check(action.getPlayerId());
                                break;
                            case FOLD:
                                fold(action.getPlayerId());
                                break;
                        }
                    } catch (GameException e) {
                        logger.warn(e.getMessage());
                    }

                    // Go next turn
                    if (bets.isEven()) {
                        setRoundRunning(false);
                    } else {
                        turns.next();
                    }

                    // Reset timer for this player
                    timer.reset(playerId);
                }
            }
        } else {

            // On timeout player fold
            fold(playerId);

            // Go next turn
            turns.next();

            // Reset timer for this player
            timer.reset(playerId);
        }
    }

    /**
     * Update Pots
     * @param bets
     */
    private void updatePots(Bets bets) {
        Pot mainPot = new Pot();
        getPots().add(0, mainPot);
        for (String playerId : bets.getAllin()) {
            Pot sidePot = new Pot();
            getPots().add(sidePot);
        }
    }

    /**
     * Assign win to players
     */
    private void reward() {
        for (Pot pot: getPots()) {
            for (String playerId : pot.getPlayers()) {
                getBoClient().win(playerId, pot.getValue());
            }
        }
    }

    /**
     * Add Game Action
     * Every game action will be customized for players
     */
    private void addGameAction() {
        THGameAction gameAction = new THGameAction(getHandId());
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

    /**
     * Raise Hand Action
     * @param playerId
     * @param amount
     * @throws GameException
     */
    private void raise(String playerId, String amount) throws GameException {
        long val = Long.parseLong(amount);
        if (val > bets.getMax()) {
            bets.bet(playerId, val);
        } else {
            throw new GameException("Action RAISE is not permitted if not overcome max bet");
        }
        bets.bet(playerId, Long.parseLong(amount));
    }

    /**
     * Bet Hand Action
     * @param playerId
     * @param amount
     * @throws GameException
     */
    private void bet(String playerId, String amount) throws GameException {
        if (bets.getMax() == 0L) {
            bets.bet(playerId, Long.parseLong(amount));
        } else {
            throw new GameException("Action BET is not permitted if max bet is zero");
        }
    }

    /**
     * Call Hand Action
     * @param playerId
     * @throws GameException
     */
    private void call(String playerId) throws GameException {
        if (roundPhase == RoundPhase.PREFLOP) {
            bets.bet(playerId, bigBlind);
        } else if (bets.getMax() > 0L) {
            bets.bet(playerId, bets.getMax());
        } else {
            throw new GameException("Action CALL is not permitted if max bet is zero");
        }
    }

    /**
     * Check Hand Anction
     * @param playerId
     * @throws GameException
     */
    private void check(String playerId) throws GameException {
        if (roundPhase == RoundPhase.PREFLOP) {
            throw new GameException("Action CHECK is not permitted during PREFLOP");
        }
        bets.bet(playerId, 0L);
    }

    /**
     * Fold Hand Action
     * @param playerId
     */
    private void fold(String playerId) {
        int seat = getSeats().indexOf(playerId);
        int order = orders.indexOf(seat);
        int turn = turns.indexOf(order);
        turns.remove(turn);
    }

    /**
     * Sit In Table Action
     * @param playerId
     * @param seat
     */
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

    /**
     * Sit Out Table Action
     * @param playerId
     */
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

    /**
     * Buy In Table Action
     * @param playerId
     * @param amount
     */
    private void buyin(String playerId, String amount) {
        getBoClient().stake(playerId, Long.parseLong(amount));
    }

    /**
     * Buy Out Table Action
     * @param playerId
     */
    private void buyout(String playerId) {
        // Not Used
        // Player are payed after each hand
    }
}
