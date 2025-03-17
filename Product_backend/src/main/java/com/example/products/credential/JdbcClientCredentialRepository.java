package com.example.products.credential;

import com.example.products.credential.Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientCredentialRepository {
    private static final Logger log= LoggerFactory.getLogger(JdbcClientCredentialRepository.class);
    private final JdbcClient jdbcClient;
    public JdbcClientCredentialRepository(JdbcClient jdbcClient) {
        this.jdbcClient=jdbcClient;
    }
    public Optional<Credential> findById(Integer id) {
        return jdbcClient.sql("SELECT id,name,password,is_logged_in FROM Credential WHERE id = :id")
                .param("id",id)
                .query(Credential.class)
                .optional();
    }
    public void create(Credential credential) {
        var updated = jdbcClient.sql("INSERT INTO Credential(id, name, password, is_logged_in) values(?, ?, ?, ?)")
                .param(1, credential.id())
                .param(2, credential.name())
                .param(3, credential.password())
                .param(4, credential.isLoggedIn())
                .update();
        Assert.state(updated==1,"Failed to create credential "+ credential.id());
    }
    public void update(Credential credential, Integer id) {
        var updated =jdbcClient.sql("update credential set id=?, name=?, password=?, is_logged_in=?, where id= ?")
                .param(List.of(credential.id(),credential.name(),credential.password(),credential.isLoggedIn()))
                .update();
        Assert.state(updated==1,"Failed to update credential "+ credential.id());
    }
    public int count() {
        return jdbcClient.sql("select * from credential").query().listOfRows().size();
    }
    public void saveAll(List<Credential> credentials) {
        credentials.stream().forEach(this::create);
    }
}
