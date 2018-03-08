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

    public SigninOut signin(SigninIn in) {
        logger.trace("signin(" + in + ")");
        SigninOut out = new SigninOut();
        out.setSessionId(UUID.randomUUID().toString());
        out.setToken(UUID.randomUUID().toString());
        UserOut user = new UserOut();
        AccountOut account = new AccountOut();
        account.setFirstName("Marco");
        account.setLastName("Romagnolo");
        account.setEmail("romagnolo.marco@gmail.com");
        account.setAge("32");
        account.setSex("M");
        WalletOut wallet = new WalletOut();
        wallet.setBalance(1900);
        user.setId("1");
        user.setUsername(in.getUsername());
        user.setAccount(account);
        user.setWallet(wallet);
        out.setUser(user);

        logger.debug("signin (" + in + ") return " + out);
        return out;
    }

    public SignoutOut signout(SignoutIn in) {
        logger.trace("signout(" + in + ")");
        SignoutOut out = new SignoutOut();
        out.setMessage("Bye Bye!");
        logger.debug("signout (" + in + ") return " + out);
        return out;
    }

    public VerifyOut verify(VerifyIn in) {
        logger.trace("verify(" + in + ")");
        VerifyOut out = new VerifyOut();
        logger.debug("verify (" + in + ") return " + out);
        return out;
    }

    public WinOut win(WinIn in) {
        logger.trace("win(" + in + ")");
        WinOut out = new WinOut();
        logger.debug("win (" + in + ") return " + out);
        return out;
    }

    public StakeOut stake(StakeIn in) {
        logger.trace("stake(" + in + ")");
        StakeOut out = new StakeOut();
        logger.debug("stake (" + in + ") return " + out);
        return out;

    }
}
