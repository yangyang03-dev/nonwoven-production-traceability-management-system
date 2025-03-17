package com.example.products.credential;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public record Credential(
        @Id
        Integer id,
        String name,
        String password,

        Integer isLoggedIn
) {

}
