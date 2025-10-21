package basicRestAssured;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ProjectTokenTest {

    JSONObject body;
    String token = "";

    @BeforeEach
    public void init() {

        body = new JSONObject();

        Response response = given()
                .auth()
                .preemptive()
                .basic("martinvo151@gmail.com", "1nd1.sm4rT%%")
                .log()
                .all()
                .when()
                .get("https://todo.ly/api/authentication/token.json");
        response.then()
                .log()
                .all()
                .statusCode(200);
        token = response.then().extract().path("TokenString");
    }


    @Test
    public void crudProject() {

        given()
                .header("Token", token)
                .body(body.toString())
                .log()
                .all()
                .when()
                .get("https://todo.ly/api/projects.json")
                .then()
                .log()
                .all()
                .statusCode(200);

        // Create
        String createProjectJson = new File("").getAbsolutePath() + "/src/test/resources/createProject.json";

        Response response = given().header("Token", token)
                .body(new File(createProjectJson))
                .when().post("https://todo.ly/api/projects.json");

        response.then()
                .log().all()
                .statusCode(200)
                .body("Content", equalTo("Project Json External"))
                .body("Icon", equalTo(5));

        int id = response.then().extract().path("Id");
        int icon = response.then().extract().path("Icon");
        String content = response.then().extract().path("Content");

        System.out.println("Id :" + id + "| Icon : " + icon + "| Content : " + content);


        // Read
        given().header("Token", token)
                .when().get("https://todo.ly/api/projects/" + id + ".json")
                .then()
                .log().all()
                .statusCode(200)
                .body("Content", equalTo("Project Json External"))
                .body("Icon", equalTo(5));


        // Update
        String updateProjectJson = new File("").getAbsolutePath() + "/src/test/resources/updateProject.json";

        given().header("Token", token)
                .body(new File(updateProjectJson))
                .when().put("https://todo.ly/api/projects/" + id + ".json")
                .then()
                .log().all()
                .statusCode(200)
                .body("Content", equalTo("Update Project Demo"))
                .body("Icon", equalTo(10));

        // Delete
        given().header("Token", token)
                .when().delete("https://todo.ly/api/projects/" + id + ".json")
                .then()
                .log().all()
                .statusCode(200)
                .body("Content", equalTo("Update Project Demo"));
    }

}
