package com.ukukhula.bursaryapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ukukhula.bursaryapi.entities.User;
import com.ukukhula.bursaryapi.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/exists/{email}")
    public Boolean userExists(@PathVariable String email) {
        return userService.userExists(email);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAll();
    }
}