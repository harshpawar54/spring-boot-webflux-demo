package com.akhp.springbootwebfluxdemo.controller;

import com.akhp.springbootwebfluxdemo.dto.ProductDTO;
import com.akhp.springbootwebfluxdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public Flux<ProductDTO> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Mono<ProductDTO> getProductById(@PathVariable String id){
        return productService.getProduct(id);
    }

    @GetMapping("/product-range")
    public Flux<ProductDTO> getProductById(@RequestParam("min") int min,@RequestParam("max") int max){
        return productService.getProductsInRange(min, max);
    }

    @PostMapping
    public Mono<ProductDTO> saveProduct(@RequestBody Mono<ProductDTO> productDTOMono){
        return productService.saveProduct(productDTOMono);
    }

    @PutMapping("/{id}")
    public Mono<ProductDTO> updateProduct(@PathVariable String id, @RequestBody Mono<ProductDTO> productDTOMono){
        return productService.updateProduct(productDTOMono, id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id){
        return productService.deleteProduct(id);
    }
}
