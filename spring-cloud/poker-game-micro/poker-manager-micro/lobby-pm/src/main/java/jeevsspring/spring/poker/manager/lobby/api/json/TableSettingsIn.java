package jeevsspring.spring.poker.manager.lobby.api.json;

public class TableSettingsIn extends PlayerSessionIn {

    private String tableId;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    @Override
    public String toString() {
        return "TableSettingsIn{" +
                "tableId='" + tableId + '\'' +
                '}';
    }
}
