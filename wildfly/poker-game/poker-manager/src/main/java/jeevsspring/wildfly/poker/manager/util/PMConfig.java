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

    private int numberOfThreads;

    @PostConstruct
    public void init() {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("poker-manager.properties");
        properties = new Properties();
        System.out.println("Loading config file: " + fileName + " with stream: " + inputStream);
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
        numberOfThreads = Integer.parseInt(properties.getProperty("numberOfThreads", "1"));
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }
}
