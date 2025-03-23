package com.example.hazelcastsync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HazelcastSyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(HazelcastSyncApplication.class, args);
    }

}
