package com.ukukhula.bursaryapi.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Location;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.ukukhula.bursaryapi.entities.User;
import com.ukukhula.bursaryapi.entities.Request.StudentApplicationRequest;
import com.ukukhula.bursaryapi.entities.Request.UpdateRoleRequest;
import com.ukukhula.bursaryapi.entities.Request.UserRequest;
import com.ukukhula.bursaryapi.services.UserService;

@CrossOrigin("*")
@RestController
@EnableMethodSecurity
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/id={id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get()); // User found, return 200 OK with user details
        } else {
            return ResponseEntity.notFound().build(); // User not found, return 404 Not Found
        }
    }

    @GetMapping("/get/{email}")
    @PreAuthorize("hasRole('ROLE_BBDSuperAdmin') or hasRole('ROLE_HOD')")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get()); // User found, return 200 OK with user details
        } else {
            return ResponseEntity.notFound().build(); // User not found, return 404 Not Found
        }
    }


    @GetMapping("/exists/{email}")
    @PreAuthorize("hasRole('ROLE_BBDSuperAdmin') or hasRole('ROLE_HOD')")
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
    @PreAuthorize("hasRole('ROLE_BBDSuperAdmin')")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ROLE_BBDSuperAdmin') or hasRole('ROLE_HOD')")
    public ResponseEntity<?> createUser(@RequestBody UserRequest user) {
        int id = userService.save(user);
        if (id != -1) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("location", location.toString());
            return ResponseEntity.ok(jsonObject);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_BBDSuperAdmin') or hasRole('ROLE_HOD')")
    public ResponseEntity<Void> updateUser(@RequestBody UserRequest userRequest) {
        int result = userService.update(userRequest);
        if (result != -1) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/UpdateRole")
    @PreAuthorize("hasRole('ROLE_BBDSuperAdmin') or hasRole('ROLE_HOD')")
    public ResponseEntity<Void> setNewRole(UpdateRoleRequest role) {
        boolean result = userService.UpdateRole(role);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}