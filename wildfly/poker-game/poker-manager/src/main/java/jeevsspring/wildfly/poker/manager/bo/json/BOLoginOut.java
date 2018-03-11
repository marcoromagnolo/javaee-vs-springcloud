package jeevsspring.wildfly.poker.manager.bo.json;

/**
 * @author Marco Romagnolo
 */
public class BOLoginOut extends BOSessionOut {

    private long time;

    private long life;

    private BOPlayerOut player;

    private BOWalletOut wallet;

    private BOAccountOut account;

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

    public BOPlayerOut getPlayer() {
        return player;
    }

    public void setPlayer(BOPlayerOut player) {
        this.player = player;
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
