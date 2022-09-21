package com.akhp.springbootwebfluxdemo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest implements Serializable {
    private static final long serialVersionUID = -6986746375915710346L;
    public String name;
    public String username;
    public String email;
    public String password;
}
