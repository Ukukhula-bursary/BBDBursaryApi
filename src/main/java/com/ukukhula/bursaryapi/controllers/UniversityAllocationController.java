package com.ukukhula.bursaryapi.controllers;

// import com.ukukhula.bursaryapi.assemblers.UniversityAllocationAssembler;
import com.ukukhula.bursaryapi.entities.UniversityAllocation;
import com.ukukhula.bursaryapi.entities.UniversityStaff;
import com.ukukhula.bursaryapi.services.UniversityAllocationService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

// @CrossOrigin("*")
@RestController
@RequestMapping("/university_allocations")
public class UniversityAllocationController {
    @Autowired
    private UniversityAllocationService universityAllocationService;

    UniversityAllocationController(UniversityAllocationService universityAllocationService) {
        this.universityAllocationService = universityAllocationService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUniversityAllocations() {

        List<UniversityAllocation> universityAllocation = universityAllocationService.getAllUniversityAllocations();

        if (Objects.isNull(universityAllocation)) {
            return new ResponseEntity<>("Unable to retrieve all university allocations",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (universityAllocation.size() == 0) {
            return new ResponseEntity<>("There are no university allocations",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(universityAllocation);
    }

    @GetMapping("/year={year}")
    public ResponseEntity<?> getUniversityAllocationsForYear(@PathVariable int year) {

        List<UniversityAllocation> universityAllocations = universityAllocationService
                .getUniversityAllocationsForYear(year);

        if (Objects.isNull(universityAllocations)) {
            return new ResponseEntity<>("Unable to retrieve university allocations for year: " + year,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (universityAllocations.size() == 0) {
            return new ResponseEntity<>("There are no university allocations for year " + year,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(universityAllocations);
    }

    @GetMapping("/university_name={universityName}")
    public ResponseEntity<?> getUniversityAllocationsForUniversity(@PathVariable String universityName) {

        List<UniversityAllocation> universityAllocations = universityAllocationService
                .getUniversityAllocationsForUniversity(universityName);

        if (Objects.isNull(universityAllocations)) {
            return new ResponseEntity<>("Unable to retrieve university allocations for university: " + universityName,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (universityAllocations.size() == 0) {
            return new ResponseEntity<>("There are no university allocations for university " + universityName,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(universityAllocations);
    }

    @GetMapping("/remaining_fund/university_name={universityName}/year={year}")
    public ResponseEntity<?> getRemainingAmountInFundForYear(@PathVariable int year, @PathVariable String universityName) {

        BigDecimal remainingAmount = universityAllocationService.getRemainingAmountInFundForYear(year, universityName);

        if (Objects.isNull(remainingAmount)) {
            return new ResponseEntity<>(
                    "Unable to retrieve remaining fund for university: " + universityName + " for year: " + year,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(remainingAmount);
    }

}