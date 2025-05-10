package com.orders.orderService;

import com.orders.orderService.model.Product;
import com.orders.orderService.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderRepository orderRepo;
    private final WebClient.Builder webClientBuilder;

    @Value("${USER_SERVICE_URL}")
    private String userServiceUrl;

    @Value("${PRODUCT_SERVICE_URL}")
    private String productServiceUrl;

    public OrderController(OrderRepository orderRepo, WebClient.Builder webClientBuilder) {
        this.orderRepo = orderRepo;
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderRepo.save(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return orderRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/details")
    public Mono<ResponseEntity<OrderDetailsResponse>> getOrderWithDetails(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepo.findById(id);
        if (optionalOrder.isEmpty()) return Mono.just(ResponseEntity.notFound().build());

        Order order = optionalOrder.get();

        Mono<User> userMono = webClientBuilder.build()
                .get()
                .uri(userServiceUrl + "/users/{id}", order.getUserId())
                .retrieve()
                .bodyToMono(User.class);

        Mono<Product> productMono = webClientBuilder.build()
                .get()
                .uri(productServiceUrl + "/products/{id}", order.getProductId())
                .retrieve()
                .bodyToMono(Product.class);

        return Mono.zip(userMono, productMono)
                .map(tuple -> ResponseEntity.ok(new OrderDetailsResponse(order, tuple.getT1(), tuple.getT2())))
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(500).build()));
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        Optional<Order> existingOrderOpt = orderRepo.findById(id);
        if (existingOrderOpt.isPresent()) {
            Order existingOrder = existingOrderOpt.get();
            existingOrder.setOrderDate(updatedOrder.getOrderDate());
            existingOrder.setProductId(updatedOrder.getProductId());
            existingOrder.setQuantity(updatedOrder.getQuantity());
            existingOrder.setUserId(updatedOrder.getUserId());

            orderRepo.save(existingOrder);
            return ResponseEntity.ok(existingOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderRepo.deleteById(id);
    }
}
