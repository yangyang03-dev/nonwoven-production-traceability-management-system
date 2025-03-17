package com.example.products.materials;

import com.example.products.credential.Credentials;
import com.example.products.credential.JdbcClientCredentialRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class MaterialJsonDataLoader implements CommandLineRunner {
    private static final Logger log= LoggerFactory.getLogger(com.example.products.materials.Material.class);
    private final JdbcClientMaterialRepository materialRepository;
    private final ObjectMapper objectMapper;
    public MaterialJsonDataLoader(JdbcClientMaterialRepository materialRepository,ObjectMapper objectMapper) {
        this.materialRepository=materialRepository;
        this.objectMapper=objectMapper;
    }
    @Override
    public void run(String... args) throws Exception {
        if (materialRepository.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/materials.json")) {
                Materials allMaterials = objectMapper.readValue(inputStream, Materials.class);
                log.info("Reading {} materials from JSON data and saving to a database", allMaterials.materials().size());
                materialRepository.saveAll(allMaterials.materials());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Materials from JSON  data because the collection contains data.");
        }
    }
}
