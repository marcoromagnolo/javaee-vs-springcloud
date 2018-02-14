package jeevsspring.wildfly.poker.manager.util;

import java.util.UUID;

/**
 * @author Marco Romagnolo
 */
public class IdGenerator {
    public static String newSessionId() {
        return UUID.randomUUID().toString();
    }

    public static String newTableId() {
        return UUID.randomUUID().toString();
    }
}
