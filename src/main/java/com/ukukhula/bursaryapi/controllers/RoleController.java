package com.ukukhula.bursaryapi.controllers;

import com.ukukhula.bursaryapi.entities.Role;
import com.ukukhula.bursaryapi.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;


    @Autowired
    RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRoles() {

        List<Role> roles = roleService.getAllRoles();

        if(Objects.isNull(roles)) {
            return new ResponseEntity<>("Unable to retrieve roles", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/id={id}")
    public ResponseEntity<?> getRoleID(@PathVariable int id) {

        Role role = roleService.getRoleById(id);

        if (Objects.isNull(role)) {
            return new ResponseEntity<>("Role with ID '" + id + "' was not able to be retrieved", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(role);
    }

    @GetMapping("/name={name}")
    public ResponseEntity<?> getRoleName(@PathVariable String name) {
        Role role = roleService.getRoleIdByName(name);

        if (Objects.isNull(role)) {
            return new ResponseEntity<>("Role with name '" + name + "' was not able to be retrieved", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(role);
    }
}
