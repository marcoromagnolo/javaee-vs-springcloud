package jeevsspring.wildfly.poker.console.util;

import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Marco Romagnolo
 */
@ApplicationScoped
public class PCConfig {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private Properties properties;

    private final String fileName = "poker-console.properties";

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
        boTargetUrl = properties.getProperty("bo.target.url", "http://localhost:8080/back-office/api");
    }

    public String getBoTargetUrl() {
        return boTargetUrl;
    }
}
