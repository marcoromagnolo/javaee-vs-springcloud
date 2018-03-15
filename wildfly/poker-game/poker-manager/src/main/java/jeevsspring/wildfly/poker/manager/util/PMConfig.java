package jeevsspring.wildfly.poker.manager.util;

import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Marco Romagnolo
 */
@Singleton
@Startup
public class PMConfig {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private Properties properties;

    private final String fileName = "poker-manager.properties";

    private String boSessionAuthUsername;

    private String boSessionAuthPassword;

    private long boSessionUpdateInterval;

    private long boTransactionUpdateInterval;

    private long lobbyTableUpdateInterval;

    private long gameActionUpdateInterval;

    private String boTargetUrl;

    @PostConstruct
    public void init() {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        properties = new Properties();
        System.out.println("Loading config file: " + fileName);
        // Loading the properties
        try {
            properties.load(inputStream);
            logger.info("Config file: " + fileName + " correctly loaded with properties: " + properties);
            configure();
        } catch (IOException e) {
            logger.error("Error loading config file: " + fileName);
        }
    }

    private void configure() {
        boTransactionUpdateInterval = Long.parseLong(properties.getProperty("bo.transaction.update.interval", "10000"));
        lobbyTableUpdateInterval = Long.parseLong(properties.getProperty("lobby.table.update.interval", "10000"));
        gameActionUpdateInterval = Long.parseLong(properties.getProperty("game.action.update.interval", "10000"));
        boSessionUpdateInterval = Long.parseLong(properties.getProperty("bo.session.update.interval", "10000"));
        boTargetUrl = properties.getProperty("bo.target.url", "http://localhost:8080/back-office/api");
        boSessionAuthUsername = properties.getProperty("bo.session.auth.username");
        boSessionAuthPassword = properties.getProperty("bo.session.auth.password");
    }

    public String getBoSessionAuthUsername() {
        return boSessionAuthUsername;
    }

    public String getBoSessionAuthPassword() {
        return boSessionAuthPassword;
    }

    public long getBoSessionUpdateInterval() {
        return boSessionUpdateInterval;
    }

    public long getLobbyTableUpdateInterval() {
        return lobbyTableUpdateInterval;
    }

    public long getBoTransactionUpdateInterval() {
        return boTransactionUpdateInterval;
    }

    public long getGameActionUpdateInterval() {
        return gameActionUpdateInterval;
    }

    public String getBoTargetUrl() {
        return boTargetUrl;
    }
}
