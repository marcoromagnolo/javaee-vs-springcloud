package jeevsspring.wildfly.poker.manager.engine.game;

import jeevsspring.wildfly.poker.manager.engine.hand.HandActionType;
import jeevsspring.wildfly.poker.manager.engine.player.Player;
import jeevsspring.wildfly.poker.manager.engine.table.TableActionType;

/**
 * @author Marco Romagnolo
 */
public class TexasHoldem {

    public void tableAction(TableActionType type) {

    }

    public void tableAction(TableActionType type, Player player) {

    }

    public void playerAction(HandActionType type) {
        switch (type) {
            case CALL:
                call();
                break;
            case CHECK:
                check();
                break;
            case FOLD:
                fold();
                break;
            case FOLD_AND_SHOW:
                fold();
                show();
                break;
            case SHOW:
                show();
                break;
            case MUCK:
                break;
            case SIT_IN:
                break;
            case SIT_OUT:
                break;
            default:

        }
    }

    public void playerAction(HandActionType type, Long amount) {
        switch (type) {
            case RAISE:
                raise(amount);
                break;
            case BET:
                bet(amount);
                break;
        }
    }

    private void show() {

    }

    private void fold() {

    }

    private void check() {

    }

    private void call() {

    }

    private void bet(Long amount) {

    }

    private void raise(Long amount) {

    }
}
