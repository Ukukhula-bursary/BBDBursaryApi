package com.ukukhula.bursaryapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ukukhula.bursaryapi.entities.University;
import com.ukukhula.bursaryapi.entities.UserRole;
import com.ukukhula.bursaryapi.services.UserRoleService;


@CrossOrigin("*")
@RestController
@RequestMapping("/roles")
public class UserRoleController {

    private UserRoleService roleService;
    public UserRoleController(UserRoleService roleService)
    {
      this.roleService=roleService;
    }

      @GetMapping("/")
  public ResponseEntity<List<UserRole>> all() {

    List<UserRole> roles = roleService.getAll();

    return ResponseEntity.ok(roles);
  }

  @PostMapping("/{id}")
  public ResponseEntity<String> roleById(@PathVariable int id) {
    // University entityModel = rolesService.addUniversity(newUniversity.getName());
    String role= roleService.getByRoleId(id);
    return ResponseEntity.ok(role);
  }

  
    
}
