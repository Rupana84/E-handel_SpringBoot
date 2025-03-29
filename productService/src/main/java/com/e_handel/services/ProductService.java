package com.e_handel.services;

import com.e_handel.entity.Product;
import com.e_handel.repository.ProductRepository;
import com.e_handel.webclient.ProductClient;
import com.e_handel.webclient.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;
   // private final ProductClient productClient; // Inject ProductClient for inter-service calls
    private final UserClient userClient;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Product with ID " + id + " not found"));
    }
/**
    public Mono<Product> getProductFromOrderService(Long id) {
       return productClient.getProduct(id);
    }
 **/
    public Mono<String> fetchUsersFromUserService() {
        return userClient.getAllUsersFromUserService();
    }


    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = getProductById(id);
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setStock(updatedProduct.getStock());
        product.setCategory(updatedProduct.getCategory());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}