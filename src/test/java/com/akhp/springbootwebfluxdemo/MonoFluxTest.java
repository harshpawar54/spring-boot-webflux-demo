package com.akhp.springbootwebfluxdemo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

    @Test
    public void testMono(){
        Mono<?> akhpApp = Mono.just("AKHP App")
//                .then(Mono.error(new RuntimeException("AKHP app Error")))
                .log();
        akhpApp.subscribe(System.out::println, e -> System.out.println(e.getMessage()));
    }

    @Test
    public void testFlux(){
        Flux<String> akhpSkills = Flux.just("Spring", "Spring Boot", "Hibernate", "MicroService")
                .concatWithValues("AWS")
                .log();
        akhpSkills.subscribe(System.out::println);
    }
}
