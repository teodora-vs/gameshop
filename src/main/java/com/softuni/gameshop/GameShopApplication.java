package com.softuni.gameshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GameShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameShopApplication.class, args);
    }

}
