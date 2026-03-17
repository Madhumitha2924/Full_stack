package com.quizapp.quizengine.controller;

import com.quizapp.quizengine.model.Product;
import com.quizapp.quizengine.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // ✅ Task 6.1 - POST: Add new product
    // Demonstrates @RequestBody
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product saved = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved); // 201 Created
    }

    // ✅ Task 6.1 - GET: Get all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(products); // 200 OK
    }

    // ✅ Task 6.2 - GET by ID
    // Demonstrates @PathVariable
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return ResponseEntity.ok(product); // 200 OK
    }

    // ✅ Task 6.3 - PUT: Update product
    // Demonstrates @PathVariable + @RequestBody together
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product updatedProduct) {

        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());

        Product saved = productRepository.save(existing);
        return ResponseEntity.ok(saved); // 200 OK
    }

    // ✅ Task 6.3 - DELETE: Delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.deleteById(id);
        return ResponseEntity.ok("Product with id " + id + " deleted successfully!");
    }

    // ✅ Task 6.4 - @RequestParam: Search products by name
    // URL example: /api/products/search?name=Laptop
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchByName(@RequestParam String name) {
        List<Product> all = productRepository.findAll();
        List<Product> result = all.stream()
                .filter(p -> p.getName().toLowerCase()
                .contains(name.toLowerCase()))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result); // 404
        }
        return ResponseEntity.status(HttpStatus.OK).body(result); // 200
    }

    // ✅ Task 6.4 - @RequestParam with multiple params
    // URL example: /api/products/filter?minPrice=100&maxPrice=1000
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterByPrice(
            @RequestParam double minPrice,
            @RequestParam double maxPrice) {

        List<Product> all = productRepository.findAll();
        List<Product> result = all.stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(result); // 404 Not Found
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result); // 200 OK
    }
}