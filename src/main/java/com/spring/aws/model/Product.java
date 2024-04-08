package com.spring.aws.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(
        name = "product" ,
        schema = "",
        indexes = {@Index(name = "product_index", columnList = "id_Product",unique = true)}
)
public class Product {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id_Product")
    int idProduct;
    @Column(name = "category")
    String category;
    @Column(name = "created")
    Date created;
    @Column(name = "name")
    String name;
    @Column(name = "price")
    double price;

}

