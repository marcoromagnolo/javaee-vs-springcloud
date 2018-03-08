package jeevsspring.wildfly.poker.console.controller;

import jeevsspring.wildfly.poker.console.util.OperatorSession;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@RequestScoped
public class LoginCtrl implements Serializable {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private String username;

    private String password;

    private FacesContext context = FacesContext.getCurrentInstance();

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

    /**
     * Login
     */
    public void login() {
        logger.trace("login() username=" + username + ", password=" + password);
        if (username.equals("admin") && password.equals("password")) {
            OperatorSession session = new OperatorSession();
            session.setId(1);
            session.setRole("Admin");
            session.setUsername("marcoromagnolo");
            context.getExternalContext().getSessionMap().put("operator", session);
            logger.debug("login() Authenticated with session=" + session);
            try {
                String contextPath = context.getExternalContext().getRequestContextPath();
                context.getExternalContext().redirect(contextPath + "/admin/dashboard.xhtml");
            } catch (IOException e) {
                logger.error(e);
            }
        } else {
            context.addMessage(null, new FacesMessage("Authentication Failed. Check username or password!"));
            logger.debug("login() Authentication failure: username=" + username + ", password=" + password);
        }
    }

    /**
     * Logout
     */
    public void logout() {
        logger.trace("logout()");
        context.getExternalContext().invalidateSession();
        try {
            String contextPath = context.getExternalContext().getRequestContextPath();
            context.getExternalContext().redirect(contextPath + "/login.xhtml");
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
