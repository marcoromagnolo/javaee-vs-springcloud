package jeevsspring.spring.poker.manager.lobby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class LobbyManagerApp {
    public static void main(String[] args) {
        SpringApplication.run(LobbyManagerApp.class, args);
    }
}
