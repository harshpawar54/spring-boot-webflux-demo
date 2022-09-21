package com.akhp.springbootwebfluxdemo;

import com.akhp.springbootwebfluxdemo.controller.ProductController;
import com.akhp.springbootwebfluxdemo.dto.ProductDTO;
import com.akhp.springbootwebfluxdemo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


//@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebFluxTest(ProductController.class)
public class SpringBootWebFluxDemoApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductService productService;

    @Test
    public void addProductTest() {
        Mono<ProductDTO> productDTOMono = Mono.just(new ProductDTO("dfsfpfogocvbuqkulqle", "Mobile", 1, 1000));
        when(productService.saveProduct(productDTOMono)).thenReturn(productDTOMono);

        webTestClient.post().uri("/products")
                .body(Mono.just(productDTOMono), ProductDTO.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getProductsTest(){
        Flux<ProductDTO> productDTOFlux =  Flux.just(new ProductDTO("dfsfpfogocvbuqkulqle", "Mobile", 1, 1000),
                new ProductDTO("dfsfpfomsjdlspwulqle", "Laptop", 1, 2000));
        when(productService.getProducts()).thenReturn(productDTOFlux);

        Flux<ProductDTO> responseBody = webTestClient.get().uri("/products")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDTO.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new ProductDTO("dfsfpfogocvbuqkulqle", "Mobile", 1, 1000))
                .expectNext(new ProductDTO("dfsfpfomsjdlspwulqle", "Laptop", 1, 2000))
                .verifyComplete();
    }

    @Test
    public void getProductTest() {
        Mono<ProductDTO> productDTOMono = Mono.just(new ProductDTO("dfsfpfogocvbuqkulqle", "Mobile", 1, 1000));
        when(productService.getProduct(any())).thenReturn(productDTOMono);

        Flux<ProductDTO> responseBody = webTestClient.get().uri("/products/dfsfpfogocvbuqkulqle")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDTO.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p -> p.getName().equalsIgnoreCase("mobile"))
                .verifyComplete();
    }

    @Test
    public void updateProductTest(){
        Mono<ProductDTO> productDTOMono = Mono.just(new ProductDTO("dfsfpfogocvbuqkulqle", "Mobile", 1, 1000));
        when(productService.updateProduct(productDTOMono, "dfsfpfogocvbuqkulqle")).thenReturn(productDTOMono);

        webTestClient.put().uri("/products/dfsfpfogocvbuqkulqle")
                .body(Mono.just(productDTOMono), ProductDTO.class)
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    public void deleteProductTest(){
        given(productService.deleteProduct(any())).willReturn(Mono.empty());

        webTestClient.delete().uri("/products/dfsfpfogocvbuqkulqle")
                .exchange()
                .expectStatus().isOk();

    }
}
