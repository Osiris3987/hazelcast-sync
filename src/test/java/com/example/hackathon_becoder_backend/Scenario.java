package com.example.hackathon_becoder_backend;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;

public class Scenario {
    static ScenarioBuilder postTransactionScene = CoreDsl
            .scenario("post transaction")
            .exec(Steps.postRequest);
}
