package jeevsspring.wildfly.poker.manager.game.engine.texasholdem;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.bo.json.BOStakeIn;
import jeevsspring.wildfly.poker.manager.bo.json.BOWinIn;
import jeevsspring.wildfly.poker.manager.game.engine.Game;
import jeevsspring.wildfly.poker.manager.game.engine.GameException;
import jeevsspring.wildfly.poker.manager.game.engine.GameTimer;
import jeevsspring.wildfly.poker.manager.game.hand.HandAction;
import jeevsspring.wildfly.poker.manager.game.hand.Pot;
import jeevsspring.wildfly.poker.manager.game.hand.Pots;
import jeevsspring.wildfly.poker.manager.game.player.Bets;
import jeevsspring.wildfly.poker.manager.game.player.Orders;
import jeevsspring.wildfly.poker.manager.game.player.Player;
import jeevsspring.wildfly.poker.manager.game.player.Turns;
import jeevsspring.wildfly.poker.manager.game.table.TableAction;
import jeevsspring.wildfly.poker.manager.util.IdGenerator;
import org.jboss.logging.Logger;

import java.util.Map;
import java.util.Queue;

public class THGame extends Game<THGameAction> {

    // JBoss Logger
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
     * @param settings
     */
    public THGame(TableSettings settings) {
       super(settings);
       logger.trace("THGame(" + settings + ")");
    }

    @Override
    public void onSettingsApply() {
        orders = new Orders(getSeats());
        timer = new GameTimer(getSettings().getActionTimeout());
    }

    /**
     * Table Action pop from queue by TableId
     * Only during hand of game not running
     *  - buyin
     *  - buyout
     *  - sitin
     *  - sitout
     * @param queue
     */
    @Override
public void actions(Queue<TableAction> queue) {
        logger.debug("actions(" + queue + ")");
        while (queue.peek() != null) {
            TableAction action = queue.poll();
            if (!isRunning()) {
                switch (action.getActionType()) {
                    case SIT_IN:
                        sitin(action.getPlayerId(), action.getOption());
                        break;
                    case SIT_OUT:
                        sitout(action.getPlayerId());
                        break;
                }
            }
        }
    }

    /**
     * Play the hand actions pop from queue by TableId
     * @param actions
     */
    @Override
    public void actions(Map<String, HandAction> actions) {
        logger.debug("onAction(" + actions + ")");

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

                    // Update Pot
                    updatePots(bets);
                    break;
                case TURN:
                    roundPhase = RoundPhase.RIVER;

                    // Discard one card
                    getCardDeck().getCard();

                    // Add 1 Community Card
                    getCommunityCards().add(getCardDeck().getCard());
                    setRoundRunning(true);

                    // Update Pot
                    updatePots(bets);
                    break;
                case RIVER:
                    roundPhase = RoundPhase.SHOWDOWN;
                    setRoundRunning(true);

                    // Update Pot
                    updatePots(bets);
                    break;
                case SHOWDOWN:
                    roundPhase = null;
                    setRoundRunning(false);

                    // Update Pot
                    updatePots(bets);

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
                    setPots(new Pots());
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
        logger.debug("round(" + actions + ")");


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
        logger.debug("updatePots(" + bets + ")");

        Pot mainPot = new Pot();
        long val = 0;
        for (long amount : bets.getPlayers().values()) {
            val += amount;
        }
        mainPot.setValue(val);
        mainPot.getPlayers().addAll(bets.getPlayers().keySet());
    }

    /**
     * Assign win to players
     */
    private void reward() {
        logger.debug("reward()");

        // Main Pot
        Pot mainPot = getPots().getMain();
        for (String playerId : getPots().getMain().getPlayers()) {
            BOWinIn in = new BOWinIn();
            in.setPlayerId(playerId);
            in.setAmount(mainPot.getValue());
            getRewards().put(playerId, mainPot.getValue());
        }

        // Side Pot
        if (!getPots().getSide().isEmpty()) {
            for (Pot pot : getPots().getSide()) {
                for (String playerId : pot.getPlayers()) {
                    BOWinIn in = new BOWinIn();
                    in.setPlayerId(playerId);
                    in.setAmount(mainPot.getValue());
                    getRewards().put(playerId, mainPot.getValue());
                }
            }
        }
        addGameAction();
    }

    /**
     * Add Game Action
     * Every game action will be customized for players
     */
    private void addGameAction() {
        THGameAction gameAction = new THGameAction(getHandId());
        gameAction.setCommunityCards(getCommunityCards());
        gameAction.setPlayers(getPlayers());
        gameAction.getPots().add(0, getPots().getMain());
        gameAction.getPots().addAll(1, getPots().getSide());
        gameAction.setSeats(getSeats().toArray());
        gameAction.setTurn(turns.current());
        gameAction.setVisitors(getVisitors());
        gameAction.setBigBlind(bigBlind);
        gameAction.setSmallBlind(smallBlind);
        gameAction.setRoundPhase(roundPhase);
        gameAction.setWinnings(getRewards());
        getGameActions().offer(gameAction);
        logger.debug("addGameAction(" + gameAction + ")");
    }

    /**
     * Raise Hand Action
     * @param playerId
     * @param amount
     * @throws GameException
     */
    private void raise(String playerId, String amount) throws GameException {
        logger.debug("raise(" + playerId + ", " + amount + ")");
        long val = Long.parseLong(amount);

        // Limit Player Raise to balance and check Allin
        boolean allin = false;
        if (val >= getPlayer(playerId).getBalance()) {
            val = getPlayer(playerId).getBalance();
            allin = true;
        }

        // Do Raise only if overcome max bet
        if (val > bets.getMax()) {
            bets.bet(playerId, val, allin);
        } else {
            throw new GameException("Action RAISE is not permitted if not overcome max bet");
        }
    }

    /**
     * Bet Hand Action
     * @param playerId
     * @param amount
     * @throws GameException
     */
    private void bet(String playerId, String amount) throws GameException {
        logger.debug("bet(" + playerId + ", " + amount + ")");
        long val = Long.parseLong(amount);

        // Limit Player Raise to balance and check Allin
        boolean allin = false;
        if (val >= getPlayer(playerId).getBalance()) {
            val = getPlayer(playerId).getBalance();
            allin = true;
        }

        // Do Bet only if max is zero
        if (bets.getMax() == 0L) {
            bets.bet(playerId, val, allin);
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
        logger.debug("call(" + playerId + ")");
        long val;

        // Check max value of bet
        if (roundPhase == RoundPhase.PREFLOP) {
            val = bigBlind;
        } else if (bets.getMax() > 0L) {
            val = bets.getMax();
        } else {
            throw new GameException("Action CALL is not permitted if max bet is zero");
        }

        // Limit Player Call to balance and check Allin
        boolean allin = false;
        if (val >= getPlayer(playerId).getBalance()) {
            val = getPlayer(playerId).getBalance();
            allin = true;
        }
        bets.bet(playerId, val, allin);
    }

    /**
     * Check Hand Anction
     * @param playerId
     * @throws GameException
     */
    private void check(String playerId) throws GameException {
        logger.debug("check(" + playerId + ")");

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
        logger.debug("fold(" + playerId + ")");

        // Leave hand
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
        logger.debug("sitin(" + playerId + ", " + seat + ")");

        // Add player to Seats
        int s = Integer.parseInt(seat);
        getSeats().add(s ,playerId);

        // Retrieve Player and add to players
        //TODO Get Player
        Player player = getPlayers().get(playerId);
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
        logger.debug("sitout(" + playerId + ")");

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

    @Override
    public String toString() {
        return "THGame{" +
                "smallBlind=" + smallBlind +
                ", bigBlind=" + bigBlind +
                ", roundPhase=" + roundPhase +
                ", bets=" + bets +
                ", orders=" + orders +
                ", turns=" + turns +
                ", timer=" + timer +
                "} " + super.toString();
    }
}
