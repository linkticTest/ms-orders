package com.spring.aws.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    private String idNumber;
    private String idType;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String cityOfResidence;
}
