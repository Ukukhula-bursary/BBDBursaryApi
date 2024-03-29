package com.ukukhula.bursaryapi.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ukukhula.bursaryapi.entities.StudentAllocation;
import com.ukukhula.bursaryapi.entities.Dto.StudentAllocationDto;
import com.ukukhula.bursaryapi.services.StudentAllocationService;

// @CrossOrigin("*")
@Controller
public class StudentAllocationController {

    private final StudentAllocationService studentAllocationService;

    public StudentAllocationController(StudentAllocationService studentAllocationService) {
        this.studentAllocationService = studentAllocationService;
    }

    @GetMapping("/student/allocation")
    @PreAuthorize("hasRole('ROLE_BBDSuperAdmin')")
    public ResponseEntity<List<StudentAllocationDto>> getAllStudentAllocations() {
        List<StudentAllocationDto> allocations = studentAllocationService.getAllStudentAllocations();
        return ResponseEntity.ok(allocations);
    }

    @GetMapping("student/allocation/{id}")
    @PreAuthorize("hasRole('ROLE_BBDSuperAdmin')")
    public ResponseEntity<StudentAllocation> getStudentAllocationById(@PathVariable int id) {
        StudentAllocation allocation = studentAllocationService.getStudentAllocationById(id);
        return allocation != null ? ResponseEntity.ok(allocation) : ResponseEntity.notFound().build();
    }

    @PostMapping("/student/allocation")
    @PreAuthorize("hasRole('ROLE_BBDSuperAdmin')")
    public ResponseEntity<StudentAllocation> createStudentAllocation(@RequestBody StudentAllocation studentAllocation) {
        StudentAllocation createdAllocation = studentAllocationService.createStudentAllocation(studentAllocation);
        return ResponseEntity.ok(createdAllocation);
    }

    @PutMapping("/student/allocation/{id}")
    @PreAuthorize("hasRole('ROLE_BBDSuperAdmin')")
    public ResponseEntity<StudentAllocation> updateStudentAllocation(
            @PathVariable int id, @RequestBody StudentAllocation updatedAllocation) {
        StudentAllocation result = studentAllocationService.updateStudentAllocation(id, updatedAllocation);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/student/allocation/{id}")
    @PreAuthorize("hasRole('ROLE_BBDSuperAdmin')")
    public ResponseEntity<Void> deleteStudentAllocation(@PathVariable int id) {
        studentAllocationService.deleteStudentAllocation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/allocation/total-spent")
    public ResponseEntity<BigDecimal> getTotalSpentByUniversity(@RequestBody Map<String, Integer> requestBody) {
        int year = requestBody.get("year");
        int universityId = requestBody.get("universityId");

        BigDecimal totalSpent = studentAllocationService.getStudentAllocationsTotalSpent(year, universityId);
        return ResponseEntity.ok(totalSpent);
    }
}