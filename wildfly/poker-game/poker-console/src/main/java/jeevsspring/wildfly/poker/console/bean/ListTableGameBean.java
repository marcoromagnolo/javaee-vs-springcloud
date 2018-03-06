package jeevsspring.wildfly.poker.console.bean;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListTableGameBean implements Serializable {

    private List<TableGameBean> tableGames;

    @PostConstruct
    public void init() {
        tableGames = new ArrayList<>();
    }

    public List<TableGameBean> getTableGames() {
        return tableGames;
    }

    public void setTableGames(List<TableGameBean> tableGames) {
        this.tableGames = tableGames;
    }
}
