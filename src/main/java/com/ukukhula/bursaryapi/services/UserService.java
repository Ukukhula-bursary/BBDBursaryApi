package com.ukukhula.bursaryapi.services;

import com.ukukhula.bursaryapi.entities.User;
import com.ukukhula.bursaryapi.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public UserService(UserRepository userRepository)
    {
        this.userRepository=userRepository;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }
    

    
    public Boolean userExists(String email) {
        return userRepository.userExists(email);
    }

    public List<User> getAll()
    {
        return userRepository.findAll();
    }
    
}
