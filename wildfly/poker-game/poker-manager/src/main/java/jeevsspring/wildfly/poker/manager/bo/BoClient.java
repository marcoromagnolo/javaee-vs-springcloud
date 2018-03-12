package jeevsspring.wildfly.poker.manager.bo;

import jeevsspring.wildfly.poker.manager.bo.json.*;
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

    public BOLoginOut login(BOLoginIn in) throws BOException {
        logger.trace("login(" + in + ")");

        BOLoginOut out = new BOLoginOut();
        out.setSessionId(UUID.randomUUID().toString());
        out.setSessionToken(UUID.randomUUID().toString());

        out.setPlayerId("1");
        out.setNickname("Poker Ace");

        out.setFirstName("Marco");
        out.setLastName("Romagnolo");
        out.setEmail("romagnolo.marco@gmail.com");
        out.setAge("32");
        out.setSex("M");

        out.setBalance(2000);

        logger.debug("login(" + in + ") return " + out);
        return out;
    }

    public BOLogoutOut logout(BOLogoutIn in) throws BOException {
        logger.trace("logout(" + in + ")");
        BOLogoutOut out = new BOLogoutOut();
        out.setMessage("Bye Bye!");
        logger.debug("logout(" + in + ") return " + out);
        return out;
    }

    public BOVerifyOut refreshAuth(BOVerifyIn in) throws BOException {
        logger.trace("verify(" + in + ")");
        BOVerifyOut out = new BOVerifyOut();
        logger.debug("verify(" + in + ") return " + out);
        return out;
    }

    public BOWinOut win(BOWinIn in) throws BOException {
        logger.trace("win(" + in + ")");
        BOWinOut out = new BOWinOut();
        logger.debug("win(" + in + ") return " + out);
        return out;
    }

    public BOStakeOut stake(BOStakeIn in) throws BOException {
        logger.trace("stake(" + in + ")");
        BOStakeOut out = new BOStakeOut();
        logger.debug("stake(" + in + ") return " + out);
        return out;

    }

    public BORefundOut refund(BORefundIn in) throws BOException {
        logger.trace("refund(" + in + ")");
        BORefundOut out = new BORefundOut();
        logger.debug("refund(" + in + ") return " + out);
        return out;
    }

    public BOWalletOut wallet(BOWalletIn in) throws BOException {
        logger.trace("refund(" + in + ")");
        BOWalletOut out = new BOWalletOut();
        logger.debug("refund(" + in + ") return " + out);
        return out;
    }

    public BOAccountOut account(BOAccountIn in) throws BOException {
        logger.trace("account(" + in + ")");
        BOAccountOut out = new BOAccountOut();
        logger.debug("account(" + in + ") return " + out);
        return out;
    }
}
