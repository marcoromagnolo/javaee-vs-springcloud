package jeevsspring.wildfly.poker.manager.bo;

import jeevsspring.wildfly.poker.manager.bo.json.*;
import jeevsspring.wildfly.poker.manager.game.player.Player;
import org.jboss.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.UUID;

/**
 * @author Marco Romagnolo
 */
@Stateless
@LocalBean
public class BoClient {

    // Jboss Logger
    private final Logger logger = Logger.getLogger(getClass());

    public Player getPlayer(String playerId) {
        return new Player("1", "Ciccio", 20000 );
    }

    public BOLoginOut login(BOLoginIn in) throws BOException {
        logger.trace("signin(" + in + ")");
        BOLoginOut out = new BOLoginOut();
        out.setSessionId(UUID.randomUUID().toString());
        out.setToken(UUID.randomUUID().toString());
        BOUserOut user = new BOUserOut();
        BOAccountOut account = new BOAccountOut();
        account.setFirstName("Marco");
        account.setLastName("Romagnolo");
        account.setEmail("romagnolo.marco@gmail.com");
        account.setAge("32");
        account.setSex("M");
        BOWalletOut wallet = new BOWalletOut();
        wallet.setBalance(1900);
        user.setId("1");
        user.setUsername(in.getUsername());
        user.setAccount(account);
        user.setWallet(wallet);
        out.setUser(user);

        logger.debug("signin (" + in + ") return " + out);
        return out;
    }

    public BOLogoutOut logout(BOLogoutIn in) throws BOException {
        logger.trace("signout(" + in + ")");
        BOLogoutOut out = new BOLogoutOut();
        out.setMessage("Bye Bye!");
        logger.debug("signout (" + in + ") return " + out);
        return out;
    }

    public BOVerifyOut verify(BOVerifyIn in) throws BOException {
        logger.trace("verify(" + in + ")");
        BOVerifyOut out = new BOVerifyOut();
        logger.debug("verify (" + in + ") return " + out);
        return out;
    }

    public BOWinOut win(BOWinIn in) throws BOException {
        logger.trace("win(" + in + ")");
        BOWinOut out = new BOWinOut();
        logger.debug("win (" + in + ") return " + out);
        return out;
    }

    public BOStakeOut stake(BOStakeIn in) throws BOException {
        logger.trace("stake(" + in + ")");
        BOStakeOut out = new BOStakeOut();
        logger.debug("stake (" + in + ") return " + out);
        return out;

    }

    public BORefundOut refund(BORefundIn in) throws BOException {
        logger.trace("refund(" + in + ")");
        BORefundOut out = new BORefundOut();
        logger.debug("refund (" + in + ") return " + out);
        return out;
    }

    public BOWalletOut wallet(BOWalletIn in) throws BOException {
        logger.trace("refund(" + in + ")");
        BOWalletOut out = new BOWalletOut();
        logger.debug("refund (" + in + ") return " + out);
        return out;
    }

    public BOAccountOut account(BOAccountIn in) throws BOException {
        logger.trace("account(" + in + ")");
        BOAccountOut out = new BOAccountOut();
        logger.debug("account (" + in + ") return " + out);
        return out;
    }
}
