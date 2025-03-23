package com.example.hazelcastsync.task;

import com.hazelcast.map.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ServerNodeScheduler {
    private final IMap<String, LocalDateTime> lockMap;
    private static final Logger LOG = LoggerFactory.getLogger(ServerNodeScheduler.class);
    @Value("${server.node.number}")
    private int nodeNumber;

    public ServerNodeScheduler(IMap<String, LocalDateTime> lockMap) {
        this.lockMap = lockMap;
    }

    @Scheduled(cron = "0/5 * * * * *")
    public void createDistributedScheduledTask() {
        lockMap.lock("message");
        try {
            LocalDateTime date = lockMap.get("message");
            if (date == null || LocalDateTime.now().isAfter(date)) {
                LOG.info("Server node {} scheduled at {}", nodeNumber, date);
                lockMap.put("message", LocalDateTime.now().plusSeconds(4));
            }
        } finally {
            lockMap.unlock("message");
        }
    }
}
