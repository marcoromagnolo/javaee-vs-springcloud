package jeevsspring.wildfly.poker.manager.engine.game;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.engine.hand.HandAction;
import jeevsspring.wildfly.poker.manager.engine.player.Player;
import jeevsspring.wildfly.poker.manager.engine.table.TableAction;

public class TexasHoldem extends Game {

    private final String tableId;
    private final TableSettings settings;
    private final BoClient boClient;

    private int smallBlind;
    private int bigBlind;
    private Phase phase;

    public TexasHoldem(String tableId, TableSettings settings, BoClient boClient) {
       super(tableId, settings);
       this.tableId = tableId;
       this.settings = settings;
       this.boClient = boClient;
    }

    @Override
    public void action(HandAction action) {
        if (!isRunning()) {
            waitStart();
            int dealerSeat = randomSeat();
            setRunning(true);
            setDealer(dealerSeat);
        }

        if (!isRoundRunning()) {
            setDealer(nextSeat(getDealer()));
            smallBlind = nextSeat(getDealer());
            bigBlind = nextSeat(smallBlind);
            setFirst(nextSeat(bigBlind));
            setRoundRunning(true);
            setHandRunning(true);
            phase = Phase.PREFLOP;
            GameAction gameAction = new GameAction(action.getHandId());
            // TODO Add info
            addGameAction(gameAction);
        }

        // Play the hand
        if (isHandRunning()) {
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
                case FOLD_AND_SHOW:
                    fold(action.getHandId(), action.getPlayerId());
                    show();
                    break;
                case SHOW:
                    show();
                    break;
                case SIT_IN:
                    sitin(action.getPlayerId(), action.getOption());
                    break;
                case SIT_OUT:
                    sitout(action.getPlayerId());
                    break;
            }
        }

        // Go ahead with game phases
        if (!isHandRunning()) {
            switch (phase) {

                case PREFLOP:
                    phase = Phase.FLOP;
                    setHandRunning(true);
                    break;
                case FLOP:
                    phase = Phase.RIVER;
                    setHandRunning(true);
                    break;
                case RIVER:
                    phase = Phase.TURN;
                    setHandRunning(true);
                    break;
                case TURN:
                    phase = Phase.SHOWDOWN;
                    setHandRunning(true);
                    break;
                case SHOWDOWN:
                    setRoundRunning(false);
                    break;
            }
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
            }
        }
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public void setSmallBlind(int smallBlind) {
        this.smallBlind = smallBlind;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public void setBigBlind(int bigBlind) {
        this.bigBlind = bigBlind;
    }

    private void raise(String handId, String playerId, String amount) {

    }

    private void bet(String handId, String playerId, String amount) {

    }

    private void call(String handId, String playerId) {

    }

    private void check(String handId, String playerId) {

    }

    private void fold(String handId, String playerId) {

    }

    private void show() {

    }

    private void sitin(String playerId, String seat) {
        int s = Integer.parseInt(seat);
        getSeats()[s] = playerId;
        Player player = boClient.getPlayer(playerId);
        player.setSeat(s);
        getPlayers().put(playerId, player);
        if (getPlayers().size() >= 2 && !isRunning()) {
            start();
        }
    }

    private void sitout(String playerId) {
        for (int i =0; i < getSeats().length; i++) {
            if (getSeats()[i].equals(playerId)) {
                getSeats()[i] = null;
            }
        }

    }

    private void buyin(String playerId, String amount) {

    }

    private void buyout(String playerId) {

    }

    private enum Phase {
        PREFLOP, FLOP, RIVER, TURN, SHOWDOWN
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }
}
