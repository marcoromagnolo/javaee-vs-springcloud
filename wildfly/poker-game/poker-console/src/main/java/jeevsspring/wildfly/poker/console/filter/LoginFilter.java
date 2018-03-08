package jeevsspring.wildfly.poker.console.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import org.jboss.logging.Logger;

/**
 * @author Marco Romagnolo
 */
public class LoginFilter implements Filter {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Nothing to do here!
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Get the Operator session
        HttpSession httpSession = ((HttpServletRequest) request).getSession(false);
        boolean loggedIn = httpSession != null && httpSession.getAttribute("operator") != null;
        logger.trace("doFilter() User is logged: " + loggedIn);

        // For the first application request there is no loginBean in the session so user needs to log in
        // For other requests loginBean is present but we need to check if user has logged in successfully
        if (loggedIn) {
            chain.doFilter(request, response);
        } else {
            logger.debug("Operator have no session, redirecting to login page");
            ((HttpServletResponse)response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/login.xhtml");
        }


    }

    @Override
    public void destroy() {
        // Nothing to do here!
    }
}
