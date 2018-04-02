package jeevsspring.spring.backoffice.player;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManagerFactory;

@EnableEurekaServer
@EnableDiscoveryClient
@SpringBootApplication
public class PlayerBOApp {
    public static void main(String[] args) {
        SpringApplication.run(PlayerBOApp.class, args);
    }
}
