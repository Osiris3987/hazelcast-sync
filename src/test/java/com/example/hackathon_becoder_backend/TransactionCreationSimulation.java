package com.example.hackathon_becoder_backend;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;


public class TransactionCreationSimulation extends Simulation {
    HttpProtocolBuilder httpProtocol = HttpDsl
            .http
            .baseUrl("http://localhost:8080");

    public TransactionCreationSimulation() {
            this.setUp(
                    Scenario.postTransactionScene.injectOpen(
                            CoreDsl.constantUsersPerSec(40).during(Duration.ofSeconds(50))
                    )
            ).protocols(httpProtocol);
    }

}
