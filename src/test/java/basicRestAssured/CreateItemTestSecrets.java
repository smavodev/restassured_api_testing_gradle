package basicRestAssured;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import utils.Credentials;
import utils.JsonSchemaUtils;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class CreateItemTestSecrets {

    String USERNAME = Credentials.getUsername();
    String PASSWORD = Credentials.getPassword();

    private static final String BASE_URL = "https://todo.ly/api/";
    private static final int PROJECT_ID = 4481104;

    @Test
    public void verifyItemCrudFlow() {

        // 1. CREATE ITEM
        JSONObject createBody = new JSONObject();
        createBody.put("Content", "Item A1");
        createBody.put("ProjectId", PROJECT_ID);

        Response createResponse = given()
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .log().all()
                .header("Content-Type", "application/json")
                .body(createBody.toString())
                .when()
                .post(BASE_URL + "items.json");

        createResponse.then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("expectedJsonSchemaCreateItems.json").using(JsonSchemaUtils.draftV4SchemaFactory()))
                .body("Content", equalTo("Item A1"))
                .body("Deleted", equalTo(false));

        int itemId = createResponse.then().extract().path("Id");

        // 2. GET ITEM BY ID
        given()
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .get(BASE_URL + "items/" + itemId + ".json")
                .then()
                .log().all()
                .statusCode(200)
                .body("Id", equalTo(itemId))
                .body("Content", equalTo("Item A1"))
                .body("Deleted", equalTo(false));

        // 3. UPDATE ITEM
        JSONObject updateBody = new JSONObject();
        updateBody.put("Content", "Item 2");

        Response updateResponse = given()
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .header("Content-Type", "application/json")
                .body(updateBody.toString())
                .when()
                .put(BASE_URL + "items/" + itemId + ".json");

        updateResponse.then()
                .log().all()
                .statusCode(200)
                .body("Id", equalTo(itemId))
                .body("Content", equalTo("Item 2"));

        // 4. DELETE ITEM
        Response deleteResponse = given()
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .delete(BASE_URL + "items/" + itemId + ".json");

        deleteResponse.then()
                .log().all()
                .statusCode(200)
                .body("Id", equalTo(itemId))
                .body("Deleted", equalTo(true));
    }

}
