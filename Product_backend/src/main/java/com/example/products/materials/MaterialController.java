package com.example.products.materials;

import com.example.products.credential.Credential;
import com.example.products.credential.CredentialRepository;
import com.example.products.product.ProductNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {
    private final MaterialRepository materialRepository;
    public MaterialController(MaterialRepository materialRepository){this.materialRepository=materialRepository;}
    @GetMapping("")
    List<Material> findAll(){
        return materialRepository.findAll();
    }
    @GetMapping("/{id}")
    Material findById(@PathVariable Integer id){
        Optional<Material> material = materialRepository.findById(id);
        if (material.isEmpty()){
            throw new ProductNotFoundException();
        }
        return material.get();
    }
    // post
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Material material) {
        materialRepository.save(material);
    }
    // put
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@RequestBody Material material,@PathVariable Integer id) {materialRepository.save(material);}
// delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping ("/{id}")
    void delete(@PathVariable Integer id) {
        materialRepository.delete(materialRepository.findById(id).get());
    }
    @GetMapping("/title/{title}")
    List<Material> findBytitle(@PathVariable String title) {
        return materialRepository.findAllBytitle(title);
    }
}
