package jeevsspring.wildfly.games.poker.common;

import org.jboss.logging.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Marco Romagnolo
 */
public class PokerConfig {

    private final Logger logger = Logger.getLogger(PokerConfig.class);
    private final String fileName = "poker.properties";
    private Properties properties;

    private PokerConfig() {
        String file = System.getProperty("jboss.server.config.dir") + "/" + fileName;
        try(FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
