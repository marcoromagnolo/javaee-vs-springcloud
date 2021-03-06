package jeevsspring.wildfly.poker.console.controller;

import jeevsspring.wildfly.poker.console.bo.BOClient;
import jeevsspring.wildfly.poker.console.bo.BOException;
import jeevsspring.wildfly.poker.console.bo.json.OperatorLoginIn;
import jeevsspring.wildfly.poker.console.bo.json.OperatorLoginOut;
import jeevsspring.wildfly.poker.console.bo.json.OperatorLogoutIn;
import jeevsspring.wildfly.poker.console.bo.json.OperatorLogoutOut;
import jeevsspring.wildfly.poker.console.util.OperatorSession;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named("loginCtrl")
@RequestScoped
public class LoginCtrl implements Serializable {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @Inject
    private BOClient boClient;

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

        //Request Json
        OperatorLoginIn in = new OperatorLoginIn();
        in.setUsername(username);
        in.setPassword(password);

        try {
            //Call service
            OperatorLoginOut out = boClient.login(in);

            // Set http session
            OperatorSession session = new OperatorSession();
            session.setId(out.getOperatorId());
            session.setSessionId(out.getSessionId());
            session.setSessionToken(out.getSessionToken());
            session.setUsername(out.getUsername());
            context.getExternalContext().getSessionMap().put("operator", session);
            logger.debug("login() Authenticated with session=" + session);
            try {
                String contextPath = context.getExternalContext().getRequestContextPath();
                context.getExternalContext().redirect(contextPath + "/admin/dashboard.xhtml");
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        } catch (BOException e) {
            logger.error(e);
            context.addMessage(null, new FacesMessage("Authentication Failed. Check username or password!"));
            logger.debug("login() Authentication failure: username=" + username + ", password=" + password);
        }
    }

    /**
     * Logout
     */
    public void logout() {
        logger.trace("logout()");

        OperatorSession session = (OperatorSession) context.getExternalContext().getSessionMap().get("operator");

        OperatorLogoutIn in = new OperatorLogoutIn();
        in.setOperatorId(session.getId());
        in.setSessionId(session.getSessionId());
        in.setSessionToken(session.getSessionToken());

        //Call service
        try {
            OperatorLogoutOut out = boClient.logout(in);
            context.getExternalContext().invalidateSession();
            try {
                String contextPath = context.getExternalContext().getRequestContextPath();
                context.getExternalContext().redirect(contextPath + "/login.xhtml");
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        } catch (BOException e) {
            logger.error(e);
        }



    }
}
