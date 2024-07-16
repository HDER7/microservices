package com.hder.products_service.controllers;

import com.hder.products_service.model.dtos.ProductRequest;
import com.hder.products_service.model.dtos.ProductResponse;
import com.hder.products_service.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest product, UriComponentsBuilder uriBuilder) {
        var p = productService.addProduct(product);
        URI url = uriBuilder.path("/api/product/{id}").buildAndExpand(p.id()).toUri();
        return ResponseEntity.created(url).body(p);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        var p = productService.getAllProducts();
        return ResponseEntity.ok().body(p);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        var product = productService.getProduct(id);
        return ResponseEntity.ok().body(product);
    }
}
