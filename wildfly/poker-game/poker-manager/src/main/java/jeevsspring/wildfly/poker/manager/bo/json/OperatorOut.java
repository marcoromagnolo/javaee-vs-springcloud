package jeevsspring.wildfly.poker.manager.bo.json;

/**
 * @author Marco Romagnolo
 */
public class OperatorOut extends SessionOut {

    private String id;
    private String username;
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

    public AccountOut getAccount() {
        return account;
    }

    public void setAccount(AccountOut account) {
        this.account = account;
    }
}
