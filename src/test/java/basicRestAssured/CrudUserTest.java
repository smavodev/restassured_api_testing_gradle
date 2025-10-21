package basicRestAssured;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class CrudUserTest {

    JSONObject body;
    String baseUrl = "https://todo.ly/api/";

    @BeforeEach
    public void init() {
        body = new JSONObject();
        body.put("Email", "fake" + new Date().getTime() + "@fake.com");
        body.put("FullName", "fake" + new Date().getTime() + "@fake.com");
        body.put("Password", "12345");
    }

    @Test
    public void verifyCRUDProjectTest() {

        // 1. CREATE user
        Response createResponse = given()
                .body(body.toString())
                .log().all()
                .when()
                .post(baseUrl + "user.json");

        createResponse.then()
                .log().all()
                .statusCode(200)
                .body("Email", equalTo(body.get("Email")));

        // Extraer el ID del usuario creado
        int userId = createResponse.then().extract().path("Id");
        String email = body.get("Email").toString();
        String password = body.get("Password").toString();

        // 2. GET user (con autenticación)
        given()
                .auth().preemptive().basic(email, password)
                .log().all()
                .when()
                .get(baseUrl + "user/" + userId + ".json")
                .then()
                .log().all()
                .statusCode(200)
                .body("Email", equalTo(email));

        // 3. UPDATE user
        JSONObject updateBody = new JSONObject();
        updateBody.put("FullName", "UPDATED");

        given()
                .auth().preemptive().basic(email, password)
                .body(updateBody.toString())
                .log().all()
                .when()
                .put(baseUrl + "user/" + userId + ".json")
                .then()
                .log().all()
                .statusCode(200)
                .body("FullName", equalTo("UPDATED"));

        // 4. DELETE user
        given()
                .auth().preemptive().basic(email, password)
                .log().all()
                .when()
                .delete(baseUrl + "user/" + userId + ".json")
                .then()
                .log().all()
                .statusCode(200)
                .body("FullName", equalTo("UPDATED"));
    }
}
