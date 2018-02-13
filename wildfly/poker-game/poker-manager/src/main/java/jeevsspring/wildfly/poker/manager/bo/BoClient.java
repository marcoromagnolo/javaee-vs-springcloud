package jeevsspring.wildfly.poker.manager.bo;

import jeevsspring.wildfly.poker.manager.bo.json.*;

import java.util.UUID;

/**
 * @author Marco Romagnolo
 */
public class BoClient {

    public SigninOut signin(SigninIn in) {
        SigninOut out = new SigninOut();
        out.setSession(UUID.randomUUID().toString());
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
}
