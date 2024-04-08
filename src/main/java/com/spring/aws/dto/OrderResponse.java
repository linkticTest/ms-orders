package com.spring.aws.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
    Response response;
    List<OrderDTO> orderDTO;
}


