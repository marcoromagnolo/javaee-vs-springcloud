package jeevsspring.spring.console;

import com.sun.faces.config.ConfigureListener;
import jeevsspring.spring.console.filter.LoginFilter;
import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContextListener;
import java.util.HashMap;

@SpringBootApplication
public class ConsoleApp {

    public static void main(String[] args) {
        SpringApplication.run(ConsoleApp.class, args);
    }

//    @Bean
//    public FacesServlet facesServlet() {
//        return new FacesServlet();
//    }

    // Register Servlet
//    @Bean
//    public ServletRegistrationBean servletRegistrationBean() {
//        ServletRegistrationBean registration = new ServletRegistrationBean(facesServlet(), "*.xhtml");
//        registration.setName("Faces Servlet");
//        registration.addInitParameter("javax.faces.CONFIG_FILES", "faces-config.xml");
////        registration.addInitParameter("javax.faces.PROJECT_STAGE", "Development");
//        registration.setLoadOnStartup(1);
//        return registration;
//    }
//
//    // Register Filter
//    @Bean
//    public FilterRegistrationBean rewriteFilter() {
//        FilterRegistrationBean rwFilter = new FilterRegistrationBean(new LoginFilter());
//        rwFilter.setName("authenticated");
//        rwFilter.setDispatcherTypes(DispatcherType.FORWARD, DispatcherType.REQUEST);
//        rwFilter.addUrlPatterns("/admin/*");
//        return rwFilter;
//    }
//
//    @Bean
//    public ServletListenerRegistrationBean<ConfigureListener> contextLoaderListenerBean() {
//        ServletListenerRegistrationBean<ConfigureListener> bean = new ServletListenerRegistrationBean<>();
//        bean.setListener(new ConfigureListener());
//        return bean;
//    }

    // Register ServletContextListener
//    @Bean
//    public ServletListenerRegistrationBean<RequestContextListener> requestContextListenerBean() {
//        ServletListenerRegistrationBean<RequestContextListener> bean = new ServletListenerRegistrationBean<>();
//        bean.setListener(new RequestContextListener());
//        return bean;
//    }
//
//    @Bean
//    public ServletListenerRegistrationBean<ContextLoaderListener> contextLoaderListenerBean() {
//        ServletListenerRegistrationBean<ContextLoaderListener> bean = new ServletListenerRegistrationBean<>();
//        bean.setListener(new ContextLoaderListener());
//        return bean;
//    }

//    @Bean
//    public EmbeddedServletContainerFactory servletContainer() {
//        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
//        TomcatContextCustomizer contextCustomizer = new TomcatContextCustomizer() {
//
//            @Override
//            public void customize(Context context) {
//                context.addWelcomeFile("/admin/dashboard.xhtml");
//                context.setWebappVersion("3.1");
//                context.addParameter("javax.faces.CONFIG_FILES", "faces-config.xml");
//                context.addWelcomeFile("/admin/dashboard.xhtml");
////                context.addServletMappingDecoded("Faces Servlet","*.xhtml");
//                context.addApplicationListener("org.springframework.web.context.request.RequestContextListener");
//
////                context.getServletContext().addServlet("Faces Servlet", "javax.faces.webapp.FacesServlet");
//
////                context.addServletMappingDecoded("*.xhtml", "Faces Servlet", true);
//
////                FilterDef filterDef = new FilterDef();
////                filterDef.setFilterName("authenticated");
////                filterDef.setFilterClass("jeevsspring.spring.console.LoginFilter");
////                filterDef.setAsyncSupported("true");
////                context.addFilterDef(filterDef);
////
////                FilterMap filterMap = new FilterMap();
////                filterMap.setFilterName("authenticated");
////                filterMap.addURLPattern("/admin/*");
////                context.addFilterMap(filterMap);
//            }
//        };
//        factory.addContextCustomizers(contextCustomizer);
//        return factory;
//    }

    /**
     * Allows the use of @Scope("view") on Spring @Component, @Service and @Controller
     * beans
     */
//    @Bean
//    public static CustomScopeConfigurer scopeConfigurer() {
//        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
//        HashMap<String, Object> hashMap = new HashMap<String, Object>();
//        hashMap.put("view", viewScope());
//        configurer.setScopes(hashMap);
//        return configurer;
//    }

//    @Bean
//    public static ViewScope viewScope() {
//        return new ViewScope();
//    }
}
