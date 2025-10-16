package basicRestAssured;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BasicRestAssured2 {

    @Test
    public void createProjectJsoExt () {

        String createProjectJson = new File("").getAbsolutePath()+"/src/test/resources/createProject.json";

        given().auth().preemptive().basic("martinvo151@gmail.com", "1nd1.sm4rT%%")
                .header("Content-Type", "application/json")
                .body(new File(createProjectJson))
                .log().all()
                .when().post("https://todo.ly/api/projects.json")
                .then()
                .log().all()
                .statusCode(200)
                .body("Content", equalTo("Project Json External"))
                .body("Icon", equalTo(5));
    }

    @Test
    public void createProjectJsoExtractAttribute () {

        String createProjectJson = new File("").getAbsolutePath()+"/src/test/resources/createProject.json";

        Response response = given().auth().preemptive().basic("martinvo151@gmail.com", "1nd1.sm4rT%%")
                .header("Content-Type", "application/json")
                .body(new File(createProjectJson))
//                .log().all()
                .when().post("https://todo.ly/api/projects.json");

                response.then()
                .log().all()
                .statusCode(200)
                .body("Content", equalTo("Project Json External"))
                .body("Icon", equalTo(5));

        int id = response.then().extract().path("Id");
        int icon = response.then().extract().path("Icon");
        String content=response.then().extract().path("Content");

        System.out.println("Id :" + id + "| Icon : " + icon + "| Content : " + content);
    }

}
