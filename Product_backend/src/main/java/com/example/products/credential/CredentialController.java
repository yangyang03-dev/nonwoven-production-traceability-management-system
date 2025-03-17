package com.example.products.credential;

import com.example.products.product.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/credentials")
public class CredentialController {
    private final CredentialRepository credentialRepository;
    public CredentialController(CredentialRepository credentialRepository){this.credentialRepository=credentialRepository;}
    @GetMapping("/{id}")
    Credential findById(@PathVariable Integer id){
        Optional<Credential> credential = credentialRepository.findById(id);
        if (credential.isEmpty()){
            throw new ProductNotFoundException();
        }
        return credential.get();
    }
    // put
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@RequestBody Credential credential,@PathVariable Integer id) {credentialRepository.save(credential);}
    @GetMapping("/name/{name}")
    Credential findByname(@PathVariable String name) {
        return credentialRepository.findByname(name);
    }
}
