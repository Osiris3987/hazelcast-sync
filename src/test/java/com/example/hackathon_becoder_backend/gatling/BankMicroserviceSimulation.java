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
                    .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huZG9lQGV4YW1wbGUuY29tIiwiaWQiOiJjN2EyZGY5NS04ZDJiLTRkM2UtYWFkNC1lOGUxM2FmNThlNWYiLCJyb2xlcyI6WyJVU0VSIl0sImlhdCI6MTcxNDA1NzYzNSwiZXhwIjoxNzE0MDYxMjM1fQ.dc0C8b8kysBJKHzLU588yMHwQLc0lgdY5tn3HV6N-5w6rjwm5OD0T42aeZGKnWodZfyRPNOsFN7DUCt1oC_Kbw")
                    .queryParam("clientId", "f0caf844-5a61-43a7-b1c2-e66971f5e08a")
                    .queryParam("legalEntityId", "b3ec6a4c-6245-419d-b884-024a69fea3eb")
                    .body(StringBody("{ \"type\": \"REFILL\", \"amount\": 1000 }"))
                    .check(HttpDsl.status().is(200))
            );

    private ScenarioBuilder postTransaction = CoreDsl
            .scenario("post and get transactions")
            .exec(postTransactions);

    public BankMicroserviceSimulation() {
        this.setUp(
                postTransaction.injectOpen(
                        CoreDsl.constantUsersPerSec(100).during(Duration.ofSeconds(30))
                )
        ).protocols(httpProtocol);
    }

}
