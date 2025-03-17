package com.example.products.product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/products")
public class ProductController {
private final ProductRepository productRepository;

public ProductController(ProductRepository productRepository) {this.productRepository = productRepository;}
    @GetMapping("")
    List<Product> findAll(){ return productRepository.findAll();}
    @GetMapping("/{id}")
    Product findById(@PathVariable Integer id){
    Optional<Product> product = productRepository.findById(id);
    if (product.isEmpty()){
         throw new ProductNotFoundException();
    }
    return product.get();
    }
    // post
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create (@Valid @RequestBody Product product) {
     productRepository.save(product);
    }
    // put
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@RequestBody Product product,@PathVariable Integer id) {productRepository.save(product);}
    // delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
    productRepository.delete(productRepository.findById(id).get());
    }
    @GetMapping("/Mcompany/{MCompany}")
    List<Product> findByMCompany(@PathVariable String MCompany) {
return productRepository.findAllByMCompany(MCompany);
    }
}
