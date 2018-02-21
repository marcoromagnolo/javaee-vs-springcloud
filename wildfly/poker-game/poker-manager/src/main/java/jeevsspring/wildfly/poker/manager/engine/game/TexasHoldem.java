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

    private boolean smallBlind;
    private boolean bigBlind;

    public TexasHoldem(String tableId, TableSettings settings, BoClient boClient) {
       super(tableId, settings);
       this.tableId = tableId;
       this.settings = settings;
       this.boClient = boClient;
    }

    @Override
    public void action(HandAction action) {
        waitStart();

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

    @Override
    public void action(TableAction action) {
        if (!isPlaying()) {
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
        if (getPlayers().size() >= 2 && !isPlaying()) {
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

}
