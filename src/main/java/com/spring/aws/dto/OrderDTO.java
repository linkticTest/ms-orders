package com.spring.aws.dto;

import com.spring.aws.model.Product;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    String idOrder;
    Date createDt;
    Date updateDt;
    String status;
    UserDTO userDTO;
    List<Product> products;
}
