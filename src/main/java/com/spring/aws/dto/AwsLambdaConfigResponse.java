package com.spring.aws.dto;

import lombok.Data;

@Data
public class AwsLambdaConfigResponse {
    int code;
    String message;
    String idOrder;
}
