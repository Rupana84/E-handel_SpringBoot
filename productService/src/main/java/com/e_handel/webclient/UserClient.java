package com.e_handel.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserClient {

    @Value("${USER_SERVICE_URL}")
    private String userServiceUrl;

    private final WebClient.Builder webClientBuilder;

    public UserClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<String> getAllUsersFromUserService() {
        return webClientBuilder.build()
                .get()
                .uri(userServiceUrl + "/users")
                .retrieve()
                .bodyToMono(String.class)
                    // return fallback or empty response
                    .onErrorResume(ex -> {
                        ex.printStackTrace(); // OR log it properly
                        return Mono.just("[]");

                });
    }
}