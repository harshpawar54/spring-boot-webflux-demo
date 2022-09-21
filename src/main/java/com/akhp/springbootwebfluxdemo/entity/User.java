package com.akhp.springbootwebfluxdemo.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
@Builder
public class User {
    private String id;
    private String name;
    private String username;
    private String email;
    private String password;
    private boolean active = true;
    private List<String> roles = new ArrayList<>();
}
