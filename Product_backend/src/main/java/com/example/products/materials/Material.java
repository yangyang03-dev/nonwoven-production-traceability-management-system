package com.example.products.materials;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public record Material(
        @Id
        Integer id,
        String title,
        String type,
        String supplier,
        Float passRate,
        Float goodRate,
        String qualityLevel,
        String Inspector,
        @Version
        Integer version
) {
}
