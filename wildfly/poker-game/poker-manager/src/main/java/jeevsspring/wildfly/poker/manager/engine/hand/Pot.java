package jeevsspring.wildfly.poker.manager.engine.hand;

import java.util.UUID;

/**
 * @author Marco Romagnolo
 */
public class Pot {

    private String id;
    private long value;

    public Pot(String id) {
        this.id = UUID.randomUUID().toString();
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
