package jeevsspring.spring.poker.manager.lobby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@EnableDiscoveryClient
@SpringBootApplication
public class LobbyManagerApp {
    public static void main(String[] args) {
        SpringApplication.run(LobbyManagerApp.class, args);
    }
}
