package com.akhp.springbootwebfluxdemo.service;

import com.akhp.springbootwebfluxdemo.dto.UserDTO;
import com.akhp.springbootwebfluxdemo.entity.User;
import com.akhp.springbootwebfluxdemo.repository.UserRepository;
import com.akhp.springbootwebfluxdemo.request.SignupRequest;
import com.akhp.springbootwebfluxdemo.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Flux<UserDTO> getUsers(){
        return userRepository.findAll().map(AppUtil::entityToDto);
    }

    public Mono<UserDTO> getUser(String id){
        return userRepository.findById(id).map(AppUtil::entityToDto);
    }

//    public Flux<UserDTO> getUsersInRange(double min, double max){
//        return userRepository.findByPriceBetween(Range.closed(min, max));
//    }

    public Mono<UserDTO> saveUser(Mono<UserDTO> userDTOMono){


        return userDTOMono
                .flatMap(p ->
                        userDTOMono.map(AppUtil::dtoToEntity)
                                .doOnNext(e -> {
                                    e.setPassword(passwordEncoder.encode(e.getPassword()));
                                }))
                .flatMap(userRepository::insert)
                .map(AppUtil::entityToDto);
    }

    public Mono<UserDTO> updateUser(Mono<UserDTO> userDTOMono, String id){
        return userRepository.findById(id)
                .flatMap(p ->
                        userDTOMono.map(AppUtil::dtoToEntity)
                                .doOnNext(e -> {
                                    e.setId(id);
                                    e.setPassword(passwordEncoder.encode(e.getPassword()));
                                })
                )
                .flatMap(userRepository::save)
                .map(AppUtil::entityToDto);
    }

    public Mono<Void> deleteUser(String id){
        return userRepository.deleteById(id);
    }

    public Mono<UserDTO> signup(SignupRequest signupRequest) {
        signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        Mono<SignupRequest> signupRequestMono =  Mono.just(signupRequest);
        return  signupRequestMono
                .map(AppUtil::requestToEntity)
                .flatMap(userRepository::insert)
                .map(AppUtil::entityToDto);
    }
}
