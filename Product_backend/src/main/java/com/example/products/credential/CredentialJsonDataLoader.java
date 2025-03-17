package com.example.products.credential;

import com.example.products.product.JdbcClientProductRepository;
import com.example.products.product.ProductJsonDataLoader;
import com.example.products.product.Products;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class CredentialJsonDataLoader implements CommandLineRunner {
    private static final Logger log= LoggerFactory.getLogger(CredentialJsonDataLoader.class);
    private final JdbcClientCredentialRepository credentialRepository;
    private final ObjectMapper objectMapper;
    public CredentialJsonDataLoader(JdbcClientCredentialRepository credentialRepository,ObjectMapper objectMapper) {
        this.credentialRepository=credentialRepository;
        this.objectMapper=objectMapper;
    }
    @Override
    public void run(String... args) throws Exception {
        if (credentialRepository.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/credentials.json")) {
                Credentials allCredentials = objectMapper.readValue(inputStream, Credentials.class);
                log.info("Reading {} credentials from JSON data and saving to a database", allCredentials.credentials().size());
                credentialRepository.saveAll(allCredentials.credentials());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Credentials from JSON  data because the collection contains data.");
        }
    }
}
