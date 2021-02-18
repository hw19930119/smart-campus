package com.sunsharing.smartcampus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartCampusSApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartCampusSApplication.class, args);
    }

}
