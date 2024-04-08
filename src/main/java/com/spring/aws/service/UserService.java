package com.spring.aws.service;

import com.spring.aws.model.User;
import com.spring.aws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUSerById(String idNumber){
        return userRepository.findById(idNumber);
    }
}
