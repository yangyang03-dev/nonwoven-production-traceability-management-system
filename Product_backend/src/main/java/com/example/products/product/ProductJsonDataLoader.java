package com.example.products.product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
@Component

public class ProductJsonDataLoader implements CommandLineRunner{
    private static final Logger log= LoggerFactory.getLogger(ProductJsonDataLoader.class);
    private final JdbcClientProductRepository productRepository;
    private final ObjectMapper objectMapper;
    public ProductJsonDataLoader(JdbcClientProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }
    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/products.json")) {
                Products allProducts = objectMapper.readValue(inputStream, Products.class);
                log.info("Reading {} products from JSON data and saving to a database", allProducts.products().size());
                productRepository.saveAll(allProducts.products());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Products from JSON  data because the collection contains data.");
        }
    }

}
