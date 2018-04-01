package jeevsspring.spring.console.controller;

import org.jboss.logging.Logger;
import org.springframework.web.context.annotation.RequestScope;

import javax.inject.Named;
import java.io.Serializable;

@Named("listTableCtrl")
@RequestScope
public class ListTableGameCtrl implements Serializable {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

}
