package com.hder.orders_service.controllers;

import com.hder.orders_service.model.dtos.OrderRequest;
import com.hder.orders_service.model.dtos.OrderResponse;
import com.hder.orders_service.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "orders-service", fallbackMethod = "placeOrderFallback")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest order, UriComponentsBuilder uriBuilder) {
        var o = orderService.placeOrder(order);
        URI url = uriBuilder.path("/api/order/{id}").buildAndExpand(o.id()).toUri();
        return ResponseEntity.created(url).body(o);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    private ResponseEntity<OrderResponse> placeOrderFallback(@RequestBody OrderRequest order,Throwable throwable) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }
}
