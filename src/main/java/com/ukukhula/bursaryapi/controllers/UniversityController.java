package com.ukukhula.bursaryapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ukukhula.bursaryapi.entities.Department;
// import com.ukukhula.bursaryapi.assemblers.UniversityModelAssembler;
import com.ukukhula.bursaryapi.entities.University;

import java.util.List;
import java.util.Objects;
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

  @Autowired
  UniversityController(UniversityService universityService) {
    // this.assembler = assembler;
    this.universityService = universityService;
  }

  @GetMapping("/all")
  public ResponseEntity<?> all() {

    List<University> universities = universityService.getAllUniversities();

    if(Objects.isNull(universities)) {
      return new ResponseEntity<>("Unable to retrieve universities", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return ResponseEntity.ok(universities);
  }

  @PostMapping("/add")
  public ResponseEntity<?> newUniversity(@RequestBody University newUniversity) {
    University insertedUniversity = universityService.addUniversity(newUniversity);
    if (Objects.isNull(insertedUniversity)) {
      return new ResponseEntity<>("Unable to add university '" + newUniversity.getUniversityName(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return ResponseEntity.ok(insertedUniversity);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> one(@PathVariable int id) {
    University university = universityService.getUniversityById(id);

    if (Objects.isNull(university)) {
      return new ResponseEntity<>("University with ID '" + id + "' was not able to be retrieved",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return ResponseEntity.ok(university);

  }
  
  @GetMapping("/name={name}")
  public ResponseEntity<?> getUniversityIdByName(@PathVariable String name) {
    University university = universityService.getUniversityIdByName(name);

    if (Objects.isNull(university)) {
        return new ResponseEntity<>("University with name '" + name + "' was not able to be retrieved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return ResponseEntity.ok(university);
  }
}
