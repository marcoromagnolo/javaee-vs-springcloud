package jeevsspring.wildfly.poker.manager.api.json;

/**
 * @author Marco Romagnolo
 */
public class ActionOut {

    private int id;
    private String handId;
    private String tableId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHandId() {
        return handId;
    }

    public void setHandId(String handId) {
        this.handId = handId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
}
