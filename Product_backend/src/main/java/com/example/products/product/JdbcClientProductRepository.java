package com.example.products.product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
@Repository
public class JdbcClientProductRepository {
    private static final Logger log= LoggerFactory.getLogger(JdbcClientProductRepository.class);
    private final JdbcClient jdbcClient;
    public JdbcClientProductRepository(JdbcClient jdbcClient) {
        this.jdbcClient=jdbcClient;
    }
    public List<Product> findAll() {
        return jdbcClient.sql("select * from product")
                .query(Product.class)
                .list();
    }
    public Optional<Product> findById(Integer id) {
        return jdbcClient.sql("SELECT id,m_company,m_destination,m_quality_level,m_time,material_id1,material_id2,ws_name1,ws_start_time1,ws_end_time1,ws_name2,ws_start_time2,ws_end_time2,ws_name3,ws_start_time3,ws_end_time3,inspection_start_time,inspection_end_time,inspection_result,responsible_person FROM Product WHERE id = :id")
                .param("id",id)
                .query(Product.class)
                .optional();
    }
    public void create(Product product) {
        var updated =jdbcClient.sql("INSERT INTO Product(id,m_company,m_destination,m_quality_level,m_time,material_id1,material_id2,ws_name1,ws_start_time1,ws_end_time1,ws_name2,ws_start_time2,ws_end_time2,ws_name3,ws_start_time3,ws_end_time3,inspection_start_time,inspection_end_time,inspection_result,responsible_person) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
                .params(List.of(product.id(), product.MCompany(), product.MDestination(), product.MQualityLevel(),product.MTime(), product.MaterialId1(),product.MaterialId2(),product.wsName1(), product.wsStartTime1(), product.wsEndTime1(), product.wsName2(), product.wsStartTime2(), product.wsEndTime2(), product.wsName3(), product.wsStartTime3(), product.wsEndTime3(), product.InspectionStartTime(), product.InspectionEndTime(), product.InspectionResult(), product.ResponsiblePerson()))
                .update();
        Assert.state(updated==1,"Failed to create product "+ product.id());
    }

    public void delete(Integer id) {
        var updated= jdbcClient.sql("delete from product where id = :id")
                .param("id",id)
                .update();
        Assert.state(updated==1,"Failed to delete product "+ id);
    }
    public int count() {
        return jdbcClient.sql("select * from product").query().listOfRows().size();
    }
    public void saveAll(List<Product> products) {
        products.stream().forEach(this::create);
    }
}
