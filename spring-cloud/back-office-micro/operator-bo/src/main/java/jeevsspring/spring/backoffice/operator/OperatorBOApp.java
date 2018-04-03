package jeevsspring.spring.backoffice.operator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableEurekaClient
@SpringBootApplication
@EnableJpaRepositories
public class OperatorBOApp {
    public static void main(String[] args) {
        SpringApplication.run(OperatorBOApp.class, args);
    }
}
