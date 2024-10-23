package ca.gbc.productservice;

import io.restassured.RestAssured;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

import org.testcontainers.utility.TestcontainersConfiguration;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");


    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

    }

    static {
        mongoDBContainer.start();

    }

    @Test
    void createProductTest() {
        String requestBody = """
                {
                    "name": "Playstation 4",
                    "description": "Special Edition",
                    "price": "800"
                }
                
                """;

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/product")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("Playstation 4"))
                .body("description", Matchers.equalTo("Special Edition")) 
                .body("price",  Matchers.equalTo(800));
    }

    @Test
    void getAllProductsTest() {

        String requestBody = """
                {
                    "name": "Playstation 4",
                    "description": "Special Edition",
                    "price": "800"
                }
                
                """;

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/product")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("Playstation 4"))
                .body("description", Matchers.equalTo("Special Edition"))
                .body("price",  Matchers.equalTo(800));


        RestAssured.given()
                .contentType("application/json")
                .when()
                .get("api/product")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", Matchers.greaterThan(0))
                .body("[0].name", Matchers.equalTo("Playstation 4"))
                .body("[0].description", Matchers.equalTo("Special Edition"))
                .body("[0].price",  Matchers.equalTo(800));
    }


    @Test
    void updateProductTest() {

    }

    @Test
    void deleteProductTest() {

    }



}
