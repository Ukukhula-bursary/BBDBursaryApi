package com.ukukhula.bursaryapi.controllers;

import com.ukukhula.bursaryapi.entities.Department;
import com.ukukhula.bursaryapi.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;


    @Autowired
    DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDepartments() {

        List<Department> departments = departmentService.getAllDepartments();

        if(Objects.isNull(departments)) {
            return new ResponseEntity<>("Unable to retrieve departments", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/id={id}")
    public ResponseEntity<?> getDepartmentID(@PathVariable int id) {

        Department department = departmentService.getDepartmentById(id);

        if (Objects.isNull(department)) {
            return new ResponseEntity<>("Department with ID '" + id + "' was not able to be retrieved", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(department);
    }

    @GetMapping("/name={name}")
    public ResponseEntity<?> getDepartmentName(@PathVariable String name) {
        Department department = departmentService.getDepartmentIdByName(name);

        if (Objects.isNull(department)) {
            return new ResponseEntity<>("Department with name '" + name + "' was not able to be retrieved", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(department);
    }
}
