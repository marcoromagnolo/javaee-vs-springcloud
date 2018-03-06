package jeevsspring.wildfly.poker.console.bean;

import java.io.Serializable;

/**
 * @author Marco Romagnolo
 */
public class OperatorBean implements Serializable {

    private Integer id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
