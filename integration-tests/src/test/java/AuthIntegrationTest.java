import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthIntegrationTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4004";
    }

    @Test
    public void shouldReturnOKWithValidToken() {
        // 1. Arrange
        // 2. act
        // 3. assert
        String loginPayload = """
            {
                "email" : "testuser@test.com",
                "password" : "password123"
            }
        """;

        Response response = given()
                // send the req
                .contentType(ContentType.JSON)
                .body(loginPayload)
                .when()
                .post("/auth/login")
                // validate the response
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract()
                .response();

        System.out.println("Generated token: "+ response.jsonPath().getString("token"));
    }

    @Test
    public void shouldReturnUnauthorizedOnInvalidLogin() {

        String loginPayload = """
            {
                "email" : "invalid_user@test.com",
                "password" : "wrongpass"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .body(loginPayload)
            .when()
            .post("/auth/login")
            .then()
            .statusCode(401);

    }
}
