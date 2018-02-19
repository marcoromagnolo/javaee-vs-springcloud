package jeevsspring.wildfly.poker.manager.engine.game;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.engine.hand.HandAction;
import jeevsspring.wildfly.poker.manager.engine.table.TableAction;

import java.util.Queue;

public class TexasHoldem extends Game {

    public TexasHoldem(String tableId, TableSettings settings) {
       super(tableId, settings);
    }

    @Override
    public void action(HandAction action) {

    }

    @Override
    public void action(TableAction action) {

    }

}
