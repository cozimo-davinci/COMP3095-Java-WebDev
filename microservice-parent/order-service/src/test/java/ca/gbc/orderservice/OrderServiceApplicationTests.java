package ca.gbc.orderservice;

import ca.gbc.orderservice.stub.InventoryClientStub;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.hamcrest.Matchers;
import org.testcontainers.utility.TestcontainersConfiguration;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

    @ServiceConnection
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("order-service")
            .withUsername("admin")
            .withPassword("password");

    @LocalServerPort
    private Integer LocalPort;

    @BeforeEach
    void setUP() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = LocalPort;
        RestAssured.registerParser("text/plain", Parser.JSON);
    }

    static {
        postgreSQLContainer.start();
    }





    @Test
    void placeOrderTest() {

        String requestBody = """
                {
                    "skuCode": "SKU0001",
                    "price": 100.00,
                    "quantity": 5
                
                }
                
                """;

        InventoryClientStub.stubInventoryCall("samsung_tv_2024",10 );

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/order")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("skuCode", Matchers.equalTo("SKU0001"))
                .body("price", Matchers.equalTo(100.0F))
                .body("quantity", Matchers.equalTo(5));
    }

}
