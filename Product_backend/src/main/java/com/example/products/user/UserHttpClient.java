package com.example.products.user;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface UserHttpClient {
    @GetExchange("/user")
    List<User> findAll();
    @GetExchange("/users/{id}")

    User findById(@PathVariable Integer id);
}
