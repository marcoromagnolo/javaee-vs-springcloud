package jeevsspring.wildfly.poker.manager.game;

import jeevsspring.wildfly.poker.manager.bo.BOException;
import jeevsspring.wildfly.poker.manager.bo.BOClient;
import jeevsspring.wildfly.poker.manager.bo.json.BOLoginIn;
import jeevsspring.wildfly.poker.manager.bo.json.BOLoginOut;
import jeevsspring.wildfly.poker.manager.bo.json.BOSessionRefreshIn;
import jeevsspring.wildfly.poker.manager.bo.json.BOSessionRefreshOut;
import jeevsspring.wildfly.poker.manager.util.PMConfig;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Calendar;
import java.util.Date;

@Singleton
@Startup
public class SystemSessionManager {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @Resource
    TimerService timerService;

    @EJB
    private BOClient boClient;

    @EJB
    private PMConfig config;

    private String sessionId;

    private String sessionToken;

    private Long sessionCreateTime;

    private long sessionExpireTime;

    @PostConstruct
    public void init() {
        long time = config.getBoSessionUpdateInterval() * 1000;
        timerService.createTimer(0, time, "Every " + time + " milliseconds");
    }

    @Timeout
    public void process(Timer timer) {
        logger.trace("process() timer info: " + timer.getInfo().toString());
        logger.trace("process() started at: " + Calendar.getInstance().getTime());

        if (needLogin()) {
            BOLoginIn in = new BOLoginIn();
            in.setUsername(config.getBoSessionAuthUsername());
            in.setPassword(config.getBoSessionAuthPassword());

            try {
                BOLoginOut bo = boClient.login(in);
                sessionId = bo.getSessionId();
                sessionToken = bo.getSessionToken();
                sessionCreateTime = bo.getSessionCreateTime();
                sessionExpireTime = bo.getSessionExpireTime();
                logger.debug("System Logged Successfully");
            } catch (BOException e) {
                logger.error(e);
                logger.debug("System Login Error");
            }
        } else {
            BOSessionRefreshIn in = new BOSessionRefreshIn();
            in.setSessionId(sessionId);
            in.setSessionToken(sessionToken);
            try {
                BOSessionRefreshOut bo = boClient.sessionRefresh(in);
                sessionId = bo.getSessionId();
                sessionToken = bo.getSessionToken();
                sessionCreateTime = bo.getSessionCreateTime();
                sessionExpireTime = bo.getSessionExpireTime();
            } catch (BOException e) {
                logger.error(e);
            }
        }

        logger.trace("process() finished at: " + Calendar.getInstance().getTime());
    }

    private boolean needLogin() {
        long now = new Date().getTime();
        return sessionCreateTime == null || (sessionCreateTime + sessionExpireTime < now);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
