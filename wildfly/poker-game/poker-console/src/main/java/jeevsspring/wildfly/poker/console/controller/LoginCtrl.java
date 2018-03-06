package jeevsspring.wildfly.poker.console.controller;

import jeevsspring.wildfly.poker.console.bean.LoginBean;

import java.io.Serializable;

public class LoginCtrl implements Serializable {

    private LoginBean login;

    public void setLogin(LoginBean login) {
        this.login = login;
    }

    public LoginBean getLogin() {
        return login;
    }
}
