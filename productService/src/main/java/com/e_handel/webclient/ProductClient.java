package com.e_handel.webclient;

import com.e_handel.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


// This client is not used currently.
// Will be used if OrderService is added later.


@Service
public class ProductClient {
    private final WebClient webClient;

    public ProductClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build(); // Going to replace with actual service URL
    }

    public Mono<Product> getProduct(Long productId) {
        return webClient.get()
                .uri("/products/{id}", productId)
                .retrieve()
                .bodyToMono(Product.class);
    }
}