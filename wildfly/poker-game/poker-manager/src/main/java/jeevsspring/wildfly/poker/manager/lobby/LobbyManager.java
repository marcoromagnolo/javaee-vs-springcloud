package jeevsspring.wildfly.poker.manager.lobby;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.engine.game.Game;
import jeevsspring.wildfly.poker.manager.engine.game.Games;
import jeevsspring.wildfly.poker.manager.engine.game.TexasHoldem;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class LobbyManager {

    private final Logger logger = Logger.getLogger(getClass());

    @EJB
    private Games games;

    @EJB
    private LobbyTables lobbyTables;

    @PostConstruct
    public void init() {

        // Make this job forever
        while (true) {

            // Check and add tables games async
            if (!lobbyTables.getCreated().isEmpty()) {
                for (String tableId : lobbyTables.getCreated().keySet()) {
                    TableSettings settings = lobbyTables.getCreated().get(tableId);
                    Game game = null;
                    switch (settings.getGameType()) {

                        case TEXAS_HOLDEM:
                            game = new TexasHoldem(tableId, settings);
                            break;

                        default:
                            logger.error("Game type unknown:  " + settings.getGameType());

                    }
                    games.add(game);
                }
            }

            //Check and update tables games async
            if (!lobbyTables.getUpdated().isEmpty()) {
                for (String tableId : lobbyTables.getUpdated().keySet()) {
                    TableSettings settings = lobbyTables.getCreated().get(tableId);
                    games.get(tableId).update(settings);
                }
            }

            //Check and drop tables games async
            if (!lobbyTables.getDropped().isEmpty()) {
                for (String tableId : lobbyTables.getUpdated().keySet()) {
                    games.get(tableId).stop();
                }
            }
        }
    }

}
