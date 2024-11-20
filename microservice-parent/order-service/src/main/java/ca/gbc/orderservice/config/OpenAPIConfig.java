package ca.gbc.orderservice.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Value("${order-service.version}")
    private String version;

    @Bean
    public OpenAPI orderServiceAPI() {
        return new OpenAPI()
                .info(new Info().title("Order Service API")
                        .description("This is a REST API for Order Service")
                        .version(version)
                        .license(new License().name("Apache 2.0")))
                        .externalDocs(new ExternalDocumentation()
                                .description("Order Service API Documentation")
                                .url("https://github.com/order-service/blob/master/LICENSE"));

    }






}