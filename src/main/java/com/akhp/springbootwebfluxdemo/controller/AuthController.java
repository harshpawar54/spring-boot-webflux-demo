package com.akhp.springbootwebfluxdemo.controller;

import com.akhp.springbootwebfluxdemo.dto.UserDTO;
import com.akhp.springbootwebfluxdemo.jwt.JwtTokenProvider;
import com.akhp.springbootwebfluxdemo.request.AuthenticationRequest;
import com.akhp.springbootwebfluxdemo.request.SignupRequest;
import com.akhp.springbootwebfluxdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import javax.validation.Valid;

/**
 * @author Harsh
 */
@RestController
@RequestMapping("/auth")
//@RequiredArgsConstructor
//@CrossOrigin(origins="http://localhost:4200")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private ReactiveAuthenticationManager authenticationManager;

    @PostMapping("/login")
    public Mono<ResponseEntity> login(@Valid @RequestBody Mono<AuthenticationRequest> authRequest) {

        return authRequest
                .flatMap(login -> this.authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()))
                        .map(this.tokenProvider::createToken)
                )
                .map(jwt -> {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getAuthenticationToken());
                    var tokenBody = Map.of("authenticationToken", jwt.getAuthenticationToken(), "username", jwt.getUsername());
                    return new ResponseEntity<>(tokenBody, httpHeaders, HttpStatus.OK);
                });

    }

    @PostMapping(value = "/signup")
    public Mono<ResponseEntity> signup(@Valid @RequestBody SignupRequest signupRequest) {

        return userService.signup(signupRequest)
            .map(user -> {
                return new ResponseEntity<>(user, null, HttpStatus.OK);
            });
    }

}
