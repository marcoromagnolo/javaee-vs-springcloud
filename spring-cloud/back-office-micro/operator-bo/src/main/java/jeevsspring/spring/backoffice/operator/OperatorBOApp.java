package jeevsspring.spring.backoffice.operator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableEurekaServer
@EnableDiscoveryClient
@SpringBootApplication
@EnableJpaRepositories
public class OperatorBOApp {
    public static void main(String[] args) {
        SpringApplication.run(OperatorBOApp.class, args);
    }
}
