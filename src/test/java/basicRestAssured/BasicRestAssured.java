package basicRestAssured;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BasicRestAssured {

    /**
     * given () -->
     *              Controla la configuracion de la peticion
     *              headers
     *              parameters
     *              body / payload
     *              authorization
     *              logs
     * when () -->
     *              Metodo HTTP [GET, POST, PUT , DELETE]
     *              URL > IP:PORT / HOSTNAME  --> URL
     * then () -->
     *              Control de la respuesta
     *              response code
     *              response body
     *              response headers
     *              response message
     *              response time
     *              assertions / extracciones
     *              logs
     *              verification > schema
     * **/

    @Test
    public void createProject () {
        given().auth().preemptive().basic("martinvo151@gmail.com", "1nd1.sm4rT%%")
                .header("Content-Type", "application/json")
                .body("{ \"Content\":\"Project Rest\", \"Icon\":4 }")
                .log().all()
                .when().post("https://todo.ly/api/projects.json")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("Content", equalTo("Project Rest"))
                .body("Icon", equalTo(4));
    }

    @Test
    public void createProjectJsonObject () {

        JSONObject bodyRequest = new JSONObject();
        bodyRequest.put("Content", "Project Rest Object");
        bodyRequest.put("Icon", 4);

        given().auth().preemptive().basic("martinvo151@gmail.com", "1nd1.sm4rT%%")
                .header("Content-Type", "application/json")
                .body(bodyRequest.toString())
                .log().all()
                .when().post("https://todo.ly/api/projects.json")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("Content", equalTo("Project Rest Object"))
                .body("Icon", equalTo(4));
    }

    @Test
    public void createProjectJsonExternal () {

        String createProjectJson = new File("").getAbsolutePath()+"/src/test/resources/createProject.json";

        given().auth().preemptive().basic("martinvo151@gmail.com", "1nd1.sm4rT%%")
                .header("Content-Type", "application/json")
                .body(new File(createProjectJson))
                .log().all()
                .when().post("https://todo.ly/api/projects.json")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("Content", equalTo("Project Json External"))
                .body("Icon", equalTo(5));
    }

}
