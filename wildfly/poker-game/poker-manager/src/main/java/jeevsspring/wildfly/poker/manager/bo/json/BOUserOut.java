package jeevsspring.wildfly.poker.manager.bo.json;

/**
 * @author Marco Romagnolo
 */
public class BOUserOut extends BOSessionOut {

    private String id;
    private String username;
    private BOWalletOut wallet;
    private BOAccountOut account;

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

    public BOWalletOut getWallet() {
        return wallet;
    }

    public void setWallet(BOWalletOut wallet) {
        this.wallet = wallet;
    }

    public BOAccountOut getAccount() {
        return account;
    }

    public void setAccount(BOAccountOut account) {
        this.account = account;
    }
}
