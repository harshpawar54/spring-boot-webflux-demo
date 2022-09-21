package com.akhp.springbootwebfluxdemo.service;

import com.akhp.springbootwebfluxdemo.dto.ProductDTO;
import com.akhp.springbootwebfluxdemo.entity.Product;
import com.akhp.springbootwebfluxdemo.repository.ProductRepository;
import com.akhp.springbootwebfluxdemo.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDTO> getProducts(){
        return productRepository.findAll().map(AppUtil::entityToDto);
    }

    public Mono<ProductDTO> getProduct(String id){
        return productRepository.findById(id).map(AppUtil::entityToDto);
    }

    public Flux<ProductDTO> getProductsInRange(double min, double max){
        return productRepository.findByPriceBetween(Range.closed(min, max));
    }

    public Mono<ProductDTO> saveProduct(Mono<ProductDTO> productDTOMono){
        return productDTOMono.map(AppUtil::dtoToEntity)
                .flatMap(productRepository::insert)
                .map(AppUtil::entityToDto);
    }

    public Mono<ProductDTO> updateProduct(Mono<ProductDTO> productDTOMono, String id){
        return productRepository.findById(id)
                .flatMap(p ->
                    productDTOMono.map(AppUtil::dtoToEntity)
                    .doOnNext(e -> e.setId(id))
                )
                .flatMap(productRepository::save)
                .map(AppUtil::entityToDto);
    }

    public Mono<Void> deleteProduct(String id){
        return productRepository.deleteById(id);
    }
}
