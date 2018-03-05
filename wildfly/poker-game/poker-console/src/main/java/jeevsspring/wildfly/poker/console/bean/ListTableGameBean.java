package jeevsspring.wildfly.poker.console.bean;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public class ListTableGameBean {

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
