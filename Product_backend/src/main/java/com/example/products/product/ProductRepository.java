package com.example.products.product;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
public interface ProductRepository extends ListCrudRepository<Product,Integer> {
    @Query
    List<Product> findAllByMCompany (String MCompany);
}
