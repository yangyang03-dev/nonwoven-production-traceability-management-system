package com.example.products.materials;

import com.example.products.credential.Credential;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface MaterialRepository extends ListCrudRepository<Material,Integer> {
    @Query
    List<Material> findAllBytitle(String title);
}
