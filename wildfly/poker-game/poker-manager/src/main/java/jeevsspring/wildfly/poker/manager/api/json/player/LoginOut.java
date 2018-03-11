package jeevsspring.wildfly.poker.manager.api.json.player;

public class LoginOut extends PlayerSessionOut {

    private long time;

    private long life;

    private String nickname;

    private AccountOut account;

    private WalletOut wallet;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getLife() {
        return life;
    }

    public void setLife(long life) {
        this.life = life;
    }

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
