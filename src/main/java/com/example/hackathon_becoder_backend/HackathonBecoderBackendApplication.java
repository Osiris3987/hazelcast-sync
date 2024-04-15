package com.example.hackathon_becoder_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class HackathonBecoderBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HackathonBecoderBackendApplication.class, args);
    }

}
