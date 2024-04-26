package com.example.hackathon_becoder_backend.gatling;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.StringBody;

public class BankMicroserviceSimulation extends Simulation {

    private HttpProtocolBuilder httpProtocol = HttpDsl
            .http
            .baseUrl("http://localhost:8080")
            .contentTypeHeader("application/json");


    private ChainBuilder getTransactionsByLegalEntityId = CoreDsl.exec(
            HttpDsl
                    .http("getting transactions by legal entity id")
                    .get("/api/v1/legalEntity/b3ec6a4c-6245-419d-b884-024a69fea3eb/transactions")
                    .check(HttpDsl.status().is(200))
    );

    private ChainBuilder authentication =
            exec(http("Authenticate")
                    .post("/api/v1/auth/login")
                    .body(StringBody("{\n" +
                            "  \"username\": \"johndoe@example.com\",\n" +
                            "  \"password\": \"oleg2004\"\n" +
                            "}")
                    )
                    .check(jmesPath("accessToken").saveAs("jwtToken"))
            );

    private ChainBuilder postTransactions =
            exec(http("creating transactions")
                    .post("/api/v1/transactions")
                    .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJpZCI6ImY3ZTNmZjlhLTA5NDItNGJhMC05MTFkLTVjZWI5YzIzNjM2NCIsInJvbGVzIjpbIlVTRVIiXSwiaWF0IjoxNzE0MDk5MjA1LCJleHAiOjE3MTQxMDI4MDV9.I1tb1ngdmQn3VQzXO8Eyi21Q3_K8ay3ZPF4f8ag6kwSLUpU9N2zti27v4a0QnT08X3t1DEaifr6vyxG4RaxTgg")
                    .queryParam("clientId", "f7e3ff9a-0942-4ba0-911d-5ceb9c236364")
                    .queryParam("legalEntityId", "a575f158-7590-47a2-a66f-e819502efe9f")
                    .body(StringBody("{ \"type\": \"REFILL\", \"amount\": 1000 }"))
                    .check(HttpDsl.status().is(200))
            );

    private ScenarioBuilder postTransaction = CoreDsl
            .scenario("post and get transactions")
            .exec(postTransactions);

    public BankMicroserviceSimulation() {
        this.setUp(
                postTransaction.injectOpen(
                        CoreDsl.constantUsersPerSec(488).during(Duration.ofSeconds(15))
                )
        ).protocols(httpProtocol);
    }

}
