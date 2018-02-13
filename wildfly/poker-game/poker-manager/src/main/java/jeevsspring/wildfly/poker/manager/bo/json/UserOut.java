package jeevsspring.wildfly.poker.manager.bo.json;

/**
 * @author Marco Romagnolo
 */
public class UserOut extends SessionOut {

    private String id;
    private String username;
    private WalletOut wallet;
    private AccountOut account;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public WalletOut getWallet() {
        return wallet;
    }

    public void setWallet(WalletOut wallet) {
        this.wallet = wallet;
    }

    public AccountOut getAccount() {
        return account;
    }

    public void setAccount(AccountOut account) {
        this.account = account;
    }
}
