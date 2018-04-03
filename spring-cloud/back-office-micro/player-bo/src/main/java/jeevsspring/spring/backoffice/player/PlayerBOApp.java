package jeevsspring.spring.backoffice.player;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PlayerBOApp {
    public static void main(String[] args) {
        SpringApplication.run(PlayerBOApp.class, args);
    }
}
