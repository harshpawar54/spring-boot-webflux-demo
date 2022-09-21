package com.akhp.springbootwebfluxdemo.util;

import com.akhp.springbootwebfluxdemo.dto.ProductDTO;
import com.akhp.springbootwebfluxdemo.dto.UserDTO;
import com.akhp.springbootwebfluxdemo.entity.Product;
import com.akhp.springbootwebfluxdemo.entity.User;
import com.akhp.springbootwebfluxdemo.request.SignupRequest;
import org.springframework.beans.BeanUtils;

public class AppUtil {

    public static ProductDTO entityToDto(Product product){
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }

    public static Product dtoToEntity(ProductDTO productDTO){
        Product product = new Product();
        BeanUtils.copyProperties(productDTO,product);
        return product;
    }

    public static UserDTO entityToDto(User user){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    public static User dtoToEntity(UserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        return user;
    }

    public static User requestToEntity(SignupRequest signupRequest){
        User user = new User();
        BeanUtils.copyProperties(signupRequest,user);
        return user;
    }
}
