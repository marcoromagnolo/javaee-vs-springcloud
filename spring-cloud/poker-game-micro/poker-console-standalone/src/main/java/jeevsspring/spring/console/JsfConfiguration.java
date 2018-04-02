package jeevsspring.spring.console;

import com.sun.faces.config.ConfigureListener;
import jeevsspring.spring.console.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
public class JsfConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private LoginFilter loginFilter;

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/admin/dashboard.xhtml");
    }

    @Bean
    public FacesServlet facesServlet() {
        return new FacesServlet();
    }

    @Bean
    public ServletRegistrationBean dispatcherRegistration(FacesServlet dispatcherServlet) {
        final ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet, "*.xhtml");
        registration.setName("Faces Servlet");
        return registration;
    }

    @Bean
    public ServletContextInitializer contextInitializer() {
        ServletContextInitializer listener = new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                servletContext.addFilter("authenticated", LoginFilter.class);
                servletContext.addListener(ConfigureListener.class);
            }
        };
        return listener;
    }

    @Bean
    public FilterRegistrationBean loginFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(loginFilter);
        registration.addUrlPatterns("/admin/*");
        registration.setName("authenticated");
        registration.setOrder(1);
        return registration;
    }
}
