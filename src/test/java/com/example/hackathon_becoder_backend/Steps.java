package com.example.hackathon_becoder_backend;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.http.HttpDsl;
import static io.gatling.javaapi.core.CoreDsl.StringBody;

public class Steps {
    public static ChainBuilder postRequest = CoreDsl.exec(
            HttpDsl
                    .http("transactions")
                    .post("/api/v1/transactions")
                    .header("Content-Type", "application/json")
                    .queryParam("clientId", "f0caf844-5a61-43a7-b1c2-e66971f5e08a")
                    .queryParam("legalEntityId", "b3ec6a4c-6245-419d-b884-024a69fea3eb")
                    .body(StringBody("{ \"type\": \"REFILL\", \"amount\": 1000 }"))
                    .check(HttpDsl.status().is(200).saveAs("shittyResponses"))
    );
}
