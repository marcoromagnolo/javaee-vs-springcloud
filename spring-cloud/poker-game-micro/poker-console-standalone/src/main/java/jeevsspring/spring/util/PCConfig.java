package jeevsspring.spring.util;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
@Service
public class PCConfig {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass().toString());

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
            logger.log(Level.SEVERE, "Error loading config file: " + fileName, e);
        }
    }

    private void configure() {
        boTargetUrl = properties.getProperty("bo.target.url", "http://localhost:8080/back-office/api");
    }

    public String getBoTargetUrl() {
        return boTargetUrl;
    }
}
