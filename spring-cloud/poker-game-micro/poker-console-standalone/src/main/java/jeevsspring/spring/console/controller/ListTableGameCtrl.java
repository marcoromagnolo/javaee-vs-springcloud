package jeevsspring.spring.console.controller;

import org.jboss.logging.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.RequestScope;

import java.io.Serializable;

@Scope("request")
@Controller("listTableCtrl")
public class ListTableGameCtrl implements Serializable {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

}
