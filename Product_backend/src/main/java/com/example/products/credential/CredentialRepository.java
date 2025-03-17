package com.example.products.credential;

import com.example.products.credential.Credential;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface CredentialRepository extends ListCrudRepository<Credential,Integer> {
   @Query
   Credential findByname (String name);
}
