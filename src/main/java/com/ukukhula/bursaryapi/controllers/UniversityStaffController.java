package com.ukukhula.bursaryapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ukukhula.bursaryapi.entities.Department;
// import com.ukukhula.bursaryapi.assemblers.UniversityStaffModelAssembler;
import com.ukukhula.bursaryapi.entities.UniversityStaff;
import com.ukukhula.bursaryapi.entities.UniversityStaffDetails;

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

import com.ukukhula.bursaryapi.services.UniversityStaffService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/universitystaff")
public class UniversityStaffController {

  private final UniversityStaffService universityStaffService;


  @Autowired
  UniversityStaffController(UniversityStaffService universityStaffService) {
    this.universityStaffService = universityStaffService;
  }

  // @GetMapping("/all")
  // public ResponseEntity<?> all() {

  //   List<UniversityStaff> universities = universityStaffService.getAllUniversities();

  //   if(Objects.isNull(universities)) {
  //     return new ResponseEntity<>("Unable to retrieve universities", HttpStatus.INTERNAL_SERVER_ERROR);
  //   }
  //   return ResponseEntity.ok(universities);
  // }

  @PostMapping("/add")
  public ResponseEntity<?> addUniversityStaff(@RequestBody UniversityStaffDetails newUniversityStaffDetails) {
    System.out.println("\n\n\n");
    System.out.println(newUniversityStaffDetails.toString());
    System.out.println("\n\n\n");


    UniversityStaff insertedUniversityStaff = universityStaffService.addUniversityStaff(newUniversityStaffDetails);
    if (Objects.isNull(insertedUniversityStaff)) {
      return new ResponseEntity<>("Unable to add universityStaff '" + newUniversityStaffDetails.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return ResponseEntity.ok(insertedUniversityStaff);
  }

  // @GetMapping("/id={id}")
  // public ResponseEntity<?> one(@PathVariable int id) {
  //   UniversityStaff universityStaff = universityStaffService.getUniversityStaffById(id);

  //   if (Objects.isNull(universityStaff)) {
  //     return new ResponseEntity<>("UniversityStaff with ID '" + id + "' was not able to be retrieved",
  //         HttpStatus.INTERNAL_SERVER_ERROR);
  //   }

  //   return ResponseEntity.ok(universityStaff);

  // }
  
  // @GetMapping("/name={name}")
  // public ResponseEntity<?> getUniversityStaffIdByName(@PathVariable String name) {
  //   UniversityStaff universityStaff = universityStaffService.getUniversityStaffIdByName(name);

  //   if (Objects.isNull(universityStaff)) {
  //       return new ResponseEntity<>("UniversityStaff with name '" + name + "' was not able to be retrieved", HttpStatus.INTERNAL_SERVER_ERROR);
  //   }

  //   return ResponseEntity.ok(universityStaff);
  // }
}
