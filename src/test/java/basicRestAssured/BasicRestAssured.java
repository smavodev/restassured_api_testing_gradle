package basicRestAssured;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
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

}
