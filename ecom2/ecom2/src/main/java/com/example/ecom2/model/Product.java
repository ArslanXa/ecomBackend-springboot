package com.example.ecom2.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String brand;
    private BigDecimal price;
    private String description;
    private String category;
    private boolean productAvailable;
    private LocalDate releaseDate;
    private Integer stockQuantity;

    private String imageName;
    private String imageType;
    @Lob  //for large object
    private byte[] imageData;

}
