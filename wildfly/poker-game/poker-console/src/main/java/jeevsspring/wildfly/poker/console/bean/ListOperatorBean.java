package jeevsspring.wildfly.poker.console.bean;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Romagnolo
 */
public class ListOperatorBean implements Serializable {

    private List<OperatorBean> operators;

    @PostConstruct
    public void init() {
        this.operators = new ArrayList<>();
    }

    public List<OperatorBean> getOperators() {
        return operators;
    }

    public void setOperators(List<OperatorBean> operators) {
        this.operators = operators;
    }
}
