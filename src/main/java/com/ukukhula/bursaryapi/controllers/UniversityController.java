package com.ukukhula.bursaryapi.controllers;

import org.springframework.web.bind.annotation.RestController;

// import com.ukukhula.bursaryapi.assemblers.UniversityModelAssembler;
import com.ukukhula.bursaryapi.entities.University;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ukukhula.bursaryapi.services.UniversityService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


// @CrossOrigin("*")
@RestController
@RequestMapping("/university")
public class UniversityController {

  private final UniversityService universityService;

  // private final UniversityModelAssembler assembler;

  UniversityController(UniversityService universityService) {
    // this.assembler = assembler;
    this.universityService = universityService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<University>> all() {

    List<University> universities = universityService.getAllUniversities();

    return ResponseEntity.ok(universities);
  }

  @PostMapping("/add")
  public ResponseEntity<?> newUniversity(@RequestBody University newUniversity) {
    University entityModel = universityService.addUniversity(newUniversity.getName());
    return ResponseEntity.ok(entityModel);
  }

  @GetMapping("/{id}")
  public University one(@PathVariable int id) {

    University university = universityService.getUniversityById(id);
    return university;
  }
}
