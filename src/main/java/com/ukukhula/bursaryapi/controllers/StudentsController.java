package com.ukukhula.bursaryapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ukukhula.bursaryapi.entities.Student;

import java.util.List;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ukukhula.bursaryapi.services.StudentsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/students")
public class StudentsController {

  private final StudentsService studentsService;


  @Autowired
  StudentsController(StudentsService studentsService) {
    this.studentsService = studentsService;
  }
  
  @GetMapping("/id={id}")
  public ResponseEntity<?> getStudentsById(@PathVariable int id) {
    Student student = studentsService.getStudentById(id);

    if (Objects.isNull(student)) {
      return new ResponseEntity<>("Student with ID '" + id + "' was not able to be retrieved",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return ResponseEntity.ok(student);

  }
  

  @GetMapping("/university_name={univeristyName}")
  public ResponseEntity<?> getStudentsByUniversityName(@PathVariable String univeristyName) {
    List<Student> students = studentsService.getStudentsByUniversityName(univeristyName);

    if (Objects.isNull(students)) {
      return new ResponseEntity<>("Students from university: '" + univeristyName + "' was not able to be retrieved",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return ResponseEntity.ok(students);

  }

  @GetMapping("/university_name={univeristyName}/active")
  public ResponseEntity<?> getActiveStudentsByUniversityName(@PathVariable String univeristyName) {
    List<Student> students = studentsService.getActiveStudentsByUniversityName(univeristyName);

    if (Objects.isNull(students)) {
      return new ResponseEntity<>("Active students from university: '" + univeristyName + "' was not able to be retrieved",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return ResponseEntity.ok(students);

  }

  @GetMapping("/all")
  public ResponseEntity<?> all() {

    List<Student> allStudents = studentsService.getAllStudents();

    if(Objects.isNull(allStudents)) {
      return new ResponseEntity<>("Unable to retrieve all university staff", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return ResponseEntity.ok(allStudents);
  }

  
}
