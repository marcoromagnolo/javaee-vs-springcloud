package jeevsspring.spring.bean;

import jeevsspring.spring.util.OperatorSession;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Romagnolo
 */
@RequestScope
public class ListOperatorBean implements Serializable {

    private List<OperatorSession> operators;

    @PostConstruct
    public void init() {
        this.operators = new ArrayList<>();
    }

    public List<OperatorSession> getOperators() {
        return operators;
    }

    public void setOperators(List<OperatorSession> operators) {
        this.operators = operators;
    }
}
