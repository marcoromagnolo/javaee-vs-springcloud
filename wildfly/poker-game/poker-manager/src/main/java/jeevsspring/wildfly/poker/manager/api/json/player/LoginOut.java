package jeevsspring.wildfly.poker.manager.api.json.player;

public class LoginOut extends PlayerSessionOut {

    private String nickname;

    private AccountOut account;

    private WalletOut wallet;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public AccountOut getAccount() {
        return account;
    }

    public void setAccount(AccountOut account) {
        this.account = account;
    }

    public WalletOut getWallet() {
        return wallet;
    }

    public void setWallet(WalletOut wallet) {
        this.wallet = wallet;
    }
}
