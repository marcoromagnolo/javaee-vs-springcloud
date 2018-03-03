package jeevsspring.wildfly.poker.manager.util;

import org.jboss.logging.Logger;

import java.util.UUID;

/**
 * @author Marco Romagnolo
 */
public class IdGenerator {

    // JBoss Logger
    private final static Logger logger = Logger.getLogger(IdGenerator.class);

    public static String newHandId() {
        logger.trace("IdGenerator :: newHandId()");
        return UUID.randomUUID().toString();
    }

    public static String newTableId() {
        logger.trace("IdGenerator :: newTableId()");
        return UUID.randomUUID().toString();
    }
}
