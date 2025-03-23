package com.example.hazelcastsync.config;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class HazelcastConfig {
    @Bean
    public Config hazelCastConfig() {
        return new Config();
    }


    @Bean
    public HazelcastInstance hazelcastInstance(Config hazelCastConfig) {
        return Hazelcast.newHazelcastInstance(hazelCastConfig);
    }

    @Bean
    public IMap<String, LocalDateTime> hazelcastMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("hazelcastTimeMap");
    }
}
