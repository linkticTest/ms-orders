package com.spring.aws.dto;

import lombok.Data;
import java.util.List;

@Data
public class AwsLambdaConfigRequest {
    List<Integer> products;
    String token;
}
