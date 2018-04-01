package jeevsspring.spring.backoffice.operator.api.json;

/**
 * @author Marco Romagnolo
 */
public class OperatorLoginIn {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "OperatorLoginIn{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
