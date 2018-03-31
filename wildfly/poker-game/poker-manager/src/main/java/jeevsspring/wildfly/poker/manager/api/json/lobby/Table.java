package jeevsspring.wildfly.poker.manager.api.json.lobby;

public class Table {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LobbyTable{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
