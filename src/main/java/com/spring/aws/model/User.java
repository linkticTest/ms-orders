package com.spring.aws.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
        name = "users" ,
        schema = "",
        indexes = {@Index(name = "users_index", columnList = "id_Number",unique = true)}
)
public class User {
    @Id
    @Column(name = "id_Number")
    private String idNumber;
    @Column(name = "id_Type")
    private String idType;
    @Column(name = "first_Name")
    private String firstName;
    @Column(name = "last_Name")
    private String lastName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "city_Of_Residence")
    private String cityOfResidence;

}
