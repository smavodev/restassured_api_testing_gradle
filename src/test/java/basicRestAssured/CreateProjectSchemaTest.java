package basicRestAssured;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class CreateProjectSchemaTest {

    @Test
    public void createaProjectVerificationsScheama(){

        String createProjectJson = new File("").getAbsolutePath()+"/src/test/resources/createProject.json";
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
                .setValidationConfiguration(ValidationConfiguration.newBuilder()
                .setDefaultVersion(SchemaVersion.DRAFTV4).freeze()).freeze();

        Response response = given().auth().preemptive().basic("martinvo151@gmail.com", "1nd1.sm4rT%%")
                .header("Content-Type", "application/json")
                .body(new File(createProjectJson))
                .when().post("https://todo.ly/api/projects.json");

        response.then()
                .log().all()
                .statusCode(200)
                .body("Content", equalTo("Project Json External"))
                .body("Icon", equalTo(5))
                .body(matchesJsonSchemaInClasspath("expectedJsonSchemaCreateProject2.json").using(jsonSchemaFactory));

        int id = response.then().extract().path("Id");
        int icon = response.then().extract().path("Icon");
        String content=response.then().extract().path("Content");

        System.out.println("Id :" + id + " | Icon : " + icon + " | Content : " + content);
    }

}
