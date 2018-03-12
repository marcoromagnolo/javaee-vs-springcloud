package jeevsspring.wildfly.backoffice;

import jeevsspring.wildfly.backoffice.api.FinanceApi;
import jeevsspring.wildfly.backoffice.api.OperatorApi;
import jeevsspring.wildfly.backoffice.api.PlayerApi;
import org.jboss.logging.Logger;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class BackOfficeApi extends Application {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @Override
    public Set<Class<?>> getClasses() {
        logger.trace("getClasses()");
        Set<Class<?>> classes = new HashSet<>();
        classes.add(FinanceApi.class);
        classes.add(OperatorApi.class);
        classes.add(PlayerApi.class);
        return classes;
    }
}
