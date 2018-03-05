package jeevsspring.wildfly.poker.console.controller;

import jeevsspring.wildfly.poker.console.bean.CredentialsBean;

public class LoginCtrl {
    private CredentialsBean credentials;

    public void setCredentials(CredentialsBean credentials) {
        this.credentials = credentials;
    }

    public CredentialsBean getCredentials() {
        return credentials;
    }
}
