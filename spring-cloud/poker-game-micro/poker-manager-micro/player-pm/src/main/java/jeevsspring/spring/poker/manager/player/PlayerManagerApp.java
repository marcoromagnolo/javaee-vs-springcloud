package jeevsspring.spring.poker.manager.player;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableEurekaClient
@SpringBootApplication
public class PlayerManagerApp {

    public static void main(String[] args) {
        SpringApplication.run(PlayerManagerApp.class, args);
    }
}
