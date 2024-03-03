package com.ukukhula.bursaryapi.controllers;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.ukukhula.bursaryapi.entities.StudentApplication;
import com.ukukhula.bursaryapi.entities.StudentApplicationDetails_ActiveStudent;
import com.ukukhula.bursaryapi.entities.StudentApplicationDetails_NewStudent;
import com.ukukhula.bursaryapi.entities.Dto.StudentApplicationDto;
import com.ukukhula.bursaryapi.entities.Request.StudentApplicationRequest;
import com.ukukhula.bursaryapi.entities.Request.UpdateStudentApplicationRequest;
import com.ukukhula.bursaryapi.exceptions.StudentApplicationException;
import com.ukukhula.bursaryapi.exceptions.ApplicationInvalidStatusException;
import com.ukukhula.bursaryapi.services.StudentApplicationService;

// @CrossOrigin("*")
@RestController
@RestControllerAdvice
@RequestMapping("/studentapplication")
public class StudentApplicationController {

    private final StudentApplicationService studentApplicationService;

    public StudentApplicationController(StudentApplicationService studentApplicationService) {
        this.studentApplicationService = studentApplicationService;
        // this.assembler = assembler;
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentApplications(@PathVariable int studentId) {

        if (studentId <= 0) {
            return ResponseEntity.badRequest().body("Student ID is not provided");
        }
        StudentApplication application = studentApplicationService.findByStudentID(studentId);

        if (application == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(application);
    }

    @PutMapping("/student/{applicationid}/{status}")
    public ResponseEntity<?> updateStudentsApplicationStatus(@PathVariable int applicationid,
            @PathVariable int statusid) {
        int rowsAffected = studentApplicationService.updateStudentsApplicationStatus(applicationid, statusid);
        return ResponseEntity.ok(rowsAffected);
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentApplicationDto>> getAllStudentApplications() {
        List<StudentApplicationDto> applications = studentApplicationService.getStudentApplicationFormated();
        return ResponseEntity.ok(applications);
    }

    @ExceptionHandler({ StudentApplicationException.class,
            ApplicationInvalidStatusException.class })

    @PostMapping("/new")
    public ResponseEntity<?> createStudentsApplication(@RequestBody StudentApplicationRequest student) {
        // grab email of the person making the request
        // grab their user idea
        int id = studentApplicationService.createApplication(student);
        if (id != -1) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateStudentsApplication(@RequestBody UpdateStudentApplicationRequest student) {

        int id = studentApplicationService.updateApplication(student);
        if (id >= 1) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/delete/{applicationID}")
    public ResponseEntity<?> deleteStudentsApplication(@PathVariable int applicationID) {

        int id = studentApplicationService.deleteApplication(applicationID);
        if (id >= 1) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/student/updateColumn/{studentID}")
    public ResponseEntity<?> updateStudentsApplicationColumnValue(@PathVariable int studentID,
            @RequestBody Map<String, String> requestBody) {

        if (requestBody.isEmpty()) {
            return ResponseEntity.badRequest().body("Request body is empty");
        }

        String columNameString = new String();

        for (String columName : requestBody.keySet()) {
            columNameString = columName;
        }

        String valueString = requestBody.get(columNameString);

        try {

            if (columNameString == "Status") {
                throw new Error("You have no authority to update Status's");
            }

            Integer rowsAffected = studentApplicationService.updateStudentsApplicationColumnValue(studentID,
                    columNameString, valueString);

            if (rowsAffected >= 1) {
                return ResponseEntity.ok("Column update successful");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Unsuccessful update");
        }

    }

    @PostMapping("/apply/new")
    public ResponseEntity<?> addStudentApplication_NewStudent(
            @RequestBody StudentApplicationDetails_NewStudent studentApplicationDetails_NewStudent) {

        StudentApplication studentApplication = studentApplicationService.studentApplication_NewStudent(
                studentApplicationDetails_NewStudent);

        if (Objects.isNull(studentApplication)) {
            return new ResponseEntity<>("Unable to add student application", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(studentApplication);
    }

    @PostMapping("/apply/active")
    public ResponseEntity<?> addStudentApplication_ActiveStudent(
            @RequestBody StudentApplicationDetails_ActiveStudent studentApplicationDetails_ActiveStudent) {

        StudentApplication studentApplication = studentApplicationService.studentApplication_ActiveStudent(
                studentApplicationDetails_ActiveStudent);

        if (Objects.isNull(studentApplication)) {
            return new ResponseEntity<>("Unable to add student application", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(studentApplication);
    }

}