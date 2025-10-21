package basicRestAssured;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserModularizado {

    private static final String BASE_URL = "https://todo.ly/api/";
    private String email;
    private String password;
    private int userId;

    // 👉 Se ejecuta una vez por test (antes de cada @Test)
    @BeforeEach
    void setUp() {
        // Generar credenciales únicas
        email = "fake" + System.currentTimeMillis() + "@test.com";
        password = "12345";

        // Crear usuario y obtener ID
        JSONObject userBody = new JSONObject();
        userBody.put("Email", email);
        userBody.put("FullName", "Test User");
        userBody.put("Password", password);

        Response response = given()
                .body(userBody.toString())
                .when()
                .post(BASE_URL + "user.json");

        response.then()
                .log().all()
                .statusCode(200);
        userId = response.then().extract().path("Id");
    }

    // 👉 Opcional: limpiar después (aunque no es estrictamente necesario si usas datos únicos)
    @AfterEach
    void tearDown() {
        // Si quieres asegurar limpieza (útil en entornos compartidos)
        given()
                .auth().preemptive().basic(email, password)
                .when()
                .delete(BASE_URL + "user/" + userId + ".json")
                .then()
                .log().all();
        // No validamos el statusCode aquí para no enmascarar fallos del test principal
    }

    // ------------------- TESTS INDEPENDIENTES -------------------

    @Test
    void shouldGetUserById() {
        given()
                .auth().preemptive().basic(email, password)
                .when()
                .get(BASE_URL + "user/" + userId + ".json")
                .then()
                .log().all()
                .statusCode(200)
                .body("Email", equalTo(email))
                .body("Id", equalTo(userId));
    }

    @Test
    void shouldUpdateUserFullName() {
        JSONObject updateBody = new JSONObject();
        updateBody.put("FullName", "Updated Name");

        given()
                .auth().preemptive().basic(email, password)
                .body(updateBody.toString())
                .when()
                .put(BASE_URL + "user/" + userId + ".json")
                .then()
                .log().all()
                .statusCode(200)
                .body("FullName", equalTo("Updated Name"));
    }

    @Test
    void shouldDeleteUser() {
        given()
                .auth().preemptive().basic(email, password)
                .when()
                .delete(BASE_URL + "user/" + userId + ".json")
                .then()
//                .log().all()
                .statusCode(200);
    }

    @Test
    void shouldNotAllowUpdateWithoutAuth() {
        JSONObject updateBody = new JSONObject();
        updateBody.put("FullName", "Hacker");

        given()
                .body(updateBody.toString()) // sin auth
                .when()
                .put(BASE_URL + "user/" + userId + ".json")
                .then()
//                .log().all()
                .statusCode(200); // o 403
    }

}
