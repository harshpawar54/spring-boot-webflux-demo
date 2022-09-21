package com.akhp.springbootwebfluxdemo;

import com.akhp.springbootwebfluxdemo.entity.User;
import com.akhp.springbootwebfluxdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

/**
 * @author hantsy
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @EventListener(value = ApplicationReadyEvent.class)
    public void init() {
        log.info("start data initialization...");


        var initPosts = this.userRepository.deleteAll()
                .thenMany(
                        Flux.just("user", "admin")
                                .flatMap(username -> {
                                    List<String> roles = "user".equals(username) ?
                                            List.of("ROLE_USER") : Arrays.asList("ROLE_USER", "ROLE_ADMIN");

                                    User user = User.builder()
                                            .roles(roles)
                                            .username(username)
                                            .name(username)
                                            .password(passwordEncoder.encode("password"))
                                            .active(true)
                                            .build();

                                    return this.userRepository.save(user);
                                })
                );

        initPosts.doOnSubscribe(data -> log.info("data:" + data))
                .subscribe(
                        data -> log.info("data:" + data), err -> log.error("error:" + err),
                        () -> log.info("done initialization...")
                );

    }

}
