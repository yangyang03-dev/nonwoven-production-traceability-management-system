package com.example.products.materials;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientMaterialRepository {
    private static final Logger log = LoggerFactory.getLogger(JdbcClientMaterialRepository.class);
    private final JdbcClient jdbcClient;

    public JdbcClientMaterialRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Material> findAll() {
        return jdbcClient.sql("select * from material")
                .query(Material.class)
                .list();
    }

    public Optional<Material> findById(Integer id) {
        return jdbcClient.sql("SELECT id,title,type,supplier,pass_rate,good_rate,quality_level,inspector FROM Material WHERE id = :id")
                .param("id", id)
                .query(Material.class)
                .optional();
    }

    public void create(Material material) {
        var updated = jdbcClient.sql("INSERT INTO Material(id,title,type,supplier,pass_rate,good_rate,quality_level,inspector) values(?,?,?,?,?,?,?,?)")
                .params(List.of(material.id(), material.title(), material.type(), material.supplier(), material.passRate(), material.goodRate(), material.qualityLevel(), material.Inspector()))
                .update();
        Assert.state(updated == 1, "Failed to create material " + material.title());
    }

    public void update(Material material, Integer id) {
        var updated = jdbcClient.sql("update material set id=?, title=?, type=?, supplier=?, pass_rate= ?,good_rate= ?,quality_level= ?,inspector= ?, where id= ?")
                .param(List.of(material.id(), material.title(), material.type(), material.supplier(), material.passRate(), material.goodRate(), material.qualityLevel(), material.Inspector()
                ))
                .update();
        Assert.state(updated == 1, "Failed to update material " + material.title());
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("delete from material where id = :id")
                .param("id", id)
                .update();
        Assert.state(updated == 1, "Failed to delete material " + id);
    }

    public int count() {
        return jdbcClient.sql("select * from material").query().listOfRows().size();
    }

    public void saveAll(List<Material> materials) {
        materials.stream().forEach(this::create);
    }
}
