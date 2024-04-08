package com.spring.aws.service;

import com.spring.aws.model.Order;
import com.spring.aws.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public void saveOrder(Order order){
        orderRepository.save(order);
    }

    public Optional<Order> getOrder(String idOrder){
        return orderRepository.findById(idOrder);
    }


    public List<Order> getOrders(String idUSer){
        return orderRepository.findByUser(idUSer);
    }
}
