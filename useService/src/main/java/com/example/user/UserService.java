package com.example.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import java.util.Optional;

import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final WebClient.Builder webClientBuilder;

    // ✅ Combined constructor for both dependencies
    public UserService(UserRepository userRepository, WebClient.Builder webClientBuilder) {
        this.userRepository = userRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    // Add these methods in UserService

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            return userRepository.save(existingUser);
        });
    }

public boolean deleteUser(Long id) {
    if (userRepository.existsById(id)) {
        userRepository.deleteById(id);
        return true;
    }
    return false;
}

    @Value("${PRODUCT_SERVICE_URL}")
    private String productServiceUrl;
    private final WebClient webClient = WebClient.create();

   public String getAllProducts() {
        return webClient.get()
                .uri(productServiceUrl + "/products")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}