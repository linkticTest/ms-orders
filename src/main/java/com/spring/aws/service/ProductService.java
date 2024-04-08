package com.spring.aws.service;

import com.spring.aws.model.Product;
import com.spring.aws.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService  {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProductsById(List<Integer> idProducts){
        return productRepository.findAllById(idProducts);
    }
}
