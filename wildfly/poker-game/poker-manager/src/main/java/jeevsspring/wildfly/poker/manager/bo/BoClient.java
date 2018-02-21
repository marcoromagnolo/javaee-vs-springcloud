package jeevsspring.wildfly.poker.manager.bo;

import jeevsspring.wildfly.poker.manager.bo.json.*;
import jeevsspring.wildfly.poker.manager.engine.player.Player;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.UUID;

/**
 * @author Marco Romagnolo
 */
@Stateless
@LocalBean
public class BoClient {

    public Player getPlayer(String playerId) {
        return new Player("1", "Ciccio", 20000 );
    }

    public SigninOut signin(SigninIn in) {
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
        return out;
    }

    public SignoutOut signout(SignoutIn in) {
        SignoutOut out = new SignoutOut();
        out.setMessage("Bye Bye!");
        return out;
    }

    public VerifyOut verify(VerifyIn in) {
        VerifyOut out = new VerifyOut();
        return out;
    }
}
