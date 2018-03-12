package jeevsspring.wildfly.backoffice.util;

import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Marco Romagnolo
 */
@Singleton
public class BOConfig {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private Properties properties;

    private final String fileName = "back-office.properties";

    private String boSessionAuthUsername;

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
        boSessionAuthUsername = properties.getProperty("bo.session.auth.username");
    }

    public String getBoSessionAuthUsername() {
        return boSessionAuthUsername;
    }

}
