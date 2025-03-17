package com.example.products.user;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

public record User(
        Integer id,
        String name,
        String username,
        String email,
        Address address,
        String phone,
        String website,
        Company company
) {
}
