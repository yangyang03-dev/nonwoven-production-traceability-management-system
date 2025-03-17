package com.example.products.product;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;


public record Product(
        @Id
        Integer id,
        String MCompany,
        String MDestination,
        String MQualityLevel,
        String MTime,
        Integer MaterialId1,
        Integer MaterialId2,
        @NotEmpty
        String wsName1,
        String wsStartTime1,
        String wsEndTime1,
        @NotEmpty
        String wsName2,
        String wsStartTime2,
        String wsEndTime2,
        @NotEmpty
        String wsName3,
        String wsStartTime3,
        String wsEndTime3,
        String InspectionStartTime,
        String InspectionEndTime,
        String InspectionResult,
        String ResponsiblePerson,
        @Version
        Integer version
){

}
