package jeevsspring.wildfly.poker.console.controller;

import jeevsspring.wildfly.poker.console.bean.OperatorBean;

import java.io.Serializable;

public class LoginCtrl implements Serializable {

    private String username;
    private String password;
    private OperatorBean operatorSession;

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

    public String login() {
        if (username.equals("admin") && password.equals("password")) {
            getOperatorSession().setId(1);
            getOperatorSession().setRole("Admin");
            getOperatorSession().setUsername("marcoromagnolo");
            return "success";
        }
        return "failure";
    }

    public void setOperatorSession(OperatorBean operatorSession) {
        this.operatorSession = operatorSession;
    }

    public OperatorBean getOperatorSession() {
        return operatorSession;
    }
}
