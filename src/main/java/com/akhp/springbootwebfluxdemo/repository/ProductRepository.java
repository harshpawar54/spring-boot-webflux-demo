package com.akhp.springbootwebfluxdemo.repository;

import com.akhp.springbootwebfluxdemo.dto.ProductDTO;
import com.akhp.springbootwebfluxdemo.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    Flux<ProductDTO> findByPriceBetween(Range<Double> priceRange);
}
