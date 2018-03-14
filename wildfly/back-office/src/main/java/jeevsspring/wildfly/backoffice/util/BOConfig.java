package jeevsspring.wildfly.backoffice.util;

import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Marco Romagnolo
 */
@ApplicationScoped
public class BOConfig {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private Properties properties;

    private final String fileName = "back-office.properties";

    private int playerSessionDuration;

    private int operatorSessionDuration;

    @PostConstruct
    public void init() {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("poker-manager.properties");
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
        playerSessionDuration = Integer.parseInt(properties.getProperty("player.session.duration", "3600"));
        operatorSessionDuration = Integer.parseInt(properties.getProperty("operator.session.duration", "3600"));
    }

    public int getPlayerSessionDuration() {
        return playerSessionDuration;
    }

    public int getOperatorSessionDuration() {
        return operatorSessionDuration;
    }
}
