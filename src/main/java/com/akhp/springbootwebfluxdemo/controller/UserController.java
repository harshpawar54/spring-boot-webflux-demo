package com.akhp.springbootwebfluxdemo.controller;

import com.akhp.springbootwebfluxdemo.dto.UserDTO;
import com.akhp.springbootwebfluxdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Flux<UserDTO> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public Mono<UserDTO> getUserById(@PathVariable String id){
        return userService.getUser(id);
    }

    @PostMapping
    public Mono<UserDTO> saveUser(@RequestBody Mono<UserDTO> userDTOMono){
        return userService.saveUser(userDTOMono);
    }

    @PutMapping("/{id}")
    public Mono<UserDTO> updateUser(@PathVariable String id, @RequestBody Mono<UserDTO> userDTOMono){
        return userService.updateUser(userDTOMono, id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable String id){
        return userService.deleteUser(id);
    }
}
