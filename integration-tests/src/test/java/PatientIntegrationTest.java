import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class PatientIntegrationTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4004";
        RestAssured.config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 5000)
                        .setParam("http.socket.timeout", 5000)
                        .setParam("http.connection-manager.timeout", 5000L));
    }

    private String getAuthToken() {
        String loginPayload = """
            {
                "email" : "testuser@test.com",
                "password" : "password123"
            }
        """;

        return given()
                .contentType(ContentType.JSON)
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract()
                .jsonPath()
                .get("token");
    }

    @Test
    public void shouldReturnPatientsWithValidToken() {
        given()
                .header("Authorization", "Bearer "+getAuthToken())
                .when()
                .get("/api/patient")
                .then()
                .statusCode(200)
                .body("patients", notNullValue());

    }
}
