package jeevsspring.spring.console.controller;

import jeevsspring.spring.console.bo.BOClient;
import jeevsspring.spring.console.bo.BOException;
import jeevsspring.spring.console.bo.json.OperatorLoginIn;
import jeevsspring.spring.console.bo.json.OperatorLoginOut;
import jeevsspring.spring.console.bo.json.OperatorLogoutIn;
import jeevsspring.spring.console.bo.json.OperatorLogoutOut;
import jeevsspring.spring.console.util.OperatorSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Scope("request")
@Controller("loginCtrl")
public class LoginCtrl implements Serializable {

    private final Logger logger = Logger.getLogger(getClass().toString());

    @Autowired
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
        logger.log(Level.FINE, "login() username=" + username + ", password=" + password);

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
            logger.log(Level.FINE, "login() Authenticated with session=" + session);
            try {
                String contextPath = context.getExternalContext().getRequestContextPath();
                context.getExternalContext().redirect(contextPath + "/admin/dashboard.xhtml");
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        } catch (BOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            context.addMessage(null, new FacesMessage("Authentication Failed. Check username or password!"));
            logger.log(Level.FINE, "login() Authentication failure: username=" + username + ", password=" + password);
        }
    }

    /**
     * Logout
     */
    public void logout() {
        logger.log(Level.FINE, "logout()");

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
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        } catch (BOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }



    }
}
