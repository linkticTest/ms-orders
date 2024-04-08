package com.spring.aws.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(
        name = "orders",
        schema = "",
        indexes = {@Index(name = "orders_index", columnList = "id_Order",unique = true)}
)
public class Order {
    @Id
    @Column(name = "id_Order")
    String idOrder;
    @Column(name = "id_User")
    String idUser;
    @Column(name = "createDt")
    Date createDt;
    @Column(name = "updateDt")
    Date updateDt;
    @Column(name = "status")
    String status;
    @Column(name = "products")
    String products;
}

