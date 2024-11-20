package ca.gbc.orderservice.config;

import ca.gbc.orderservice.client.InventoryClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Value("${inventory.service.url}")
    private String invnetoryServiceUrl;


    @Bean
    public InventoryClient inventoryClient() {

        RestClient restClient = RestClient.builder()
                .baseUrl(invnetoryServiceUrl)
                .build();

        var restClientAdapter =  RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(InventoryClient.class);

    }



}