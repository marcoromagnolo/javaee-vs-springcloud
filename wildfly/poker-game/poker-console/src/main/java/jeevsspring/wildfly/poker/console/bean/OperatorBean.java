package jeevsspring.wildfly.poker.console.bean;

import java.io.Serializable;

/**
 * @author Marco Romagnolo
 */
public class OperatorBean implements Serializable {

    private int id;
    private String username;
    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
