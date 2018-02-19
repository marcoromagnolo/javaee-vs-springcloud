package jeevsspring.wildfly.poker.manager.engine.game;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.engine.hand.HandAction;
import jeevsspring.wildfly.poker.manager.engine.table.TableAction;

public class TexasHoldem extends Game {

    public TexasHoldem(String tableId, TableSettings settings) {
       super(tableId, settings);
    }

    @Override
    public GameAction action(HandAction action) {
        return null;
    }

    @Override
    public GameAction action(TableAction action) {
        return null;
    }

    @Override
    public GameAction sync() {
        return null;
    }

}
