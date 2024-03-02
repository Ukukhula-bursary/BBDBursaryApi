package com.ukukhula.bursaryapi.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ukukhula.bursaryapi.entities.User;
import com.ukukhula.bursaryapi.entities.Request.StudentApplicationRequest;
import com.ukukhula.bursaryapi.entities.Request.UpdateRoleRequest;
import com.ukukhula.bursaryapi.entities.Request.UserRequest;
import com.ukukhula.bursaryapi.services.UserService;

// @CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get()); // User found, return 200 OK with user details
        } else {
            return ResponseEntity.notFound().build(); // User not found, return 404 Not Found
        }
    }


    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> userExists(@PathVariable String email) {
        boolean exists = userService.userExists(email);
        if (exists) {
            return ResponseEntity.ok(true); // User exists, return 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false); // User does not exist, return 400 Bad
                                                                              // Request
        }
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping("/new")
    public ResponseEntity<UserRequest> createUser(@RequestBody UserRequest user) {
        int id = userService.save(user);
        if (id != -1) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateUser(@RequestBody UserRequest userRequest) {
        int result = userService.update(userRequest);
        if (result != -1) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/UpdateRole")
    public ResponseEntity<Void> setNewRole(UpdateRoleRequest role) {
        boolean result = userService.UpdateRole(role);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}