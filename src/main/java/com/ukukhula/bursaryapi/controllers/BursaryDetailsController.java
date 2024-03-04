package com.ukukhula.bursaryapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ukukhula.bursaryapi.entities.Department;
// import com.ukukhula.bursaryapi.assemblers.BursaryDetailsModelAssembler;
import com.ukukhula.bursaryapi.entities.BursaryDetails;

import java.math.BigDecimal;
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

import com.ukukhula.bursaryapi.services.BursaryDetailsService;

import net.minidev.json.JSONObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/bursarydetails")
public class BursaryDetailsController {

  private final BursaryDetailsService bursaryDetailsService;


  @Autowired
  BursaryDetailsController(BursaryDetailsService bursaryDetailsService) {
    this.bursaryDetailsService = bursaryDetailsService;
  }

  @GetMapping("/year={year}")
  public ResponseEntity<?> getBursaryDetailsByYear(@PathVariable int year) {

    BursaryDetails bursaryDetailsForYear = bursaryDetailsService.getBursaryDetailsByYear(year);

    if(Objects.isNull(bursaryDetailsForYear)) {
      return new ResponseEntity<>("Unable to retrieve bursary details for year " + year, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return ResponseEntity.ok(bursaryDetailsForYear);
  }

  @PostMapping("/even_distribute")
  public ResponseEntity<?> addBursaryDetails(@RequestBody JSONObject jsonObject) {

    int year = (int) jsonObject.getAsNumber("year");
    System.out.println("\n\n\n " + year + "\n\n\n");

    System.out.println("Got here controller");
    BigDecimal evenDistributionAmount = bursaryDetailsService.evenlyDistributeBursary(year);
    if (Objects.isNull(evenDistributionAmount)) {
      System.out.println("\n\n\n\nObject is null ##\n\n\n");
      return new ResponseEntity<>("Unable to evenly distribute funds for year: " + year, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return ResponseEntity.ok(evenDistributionAmount);
  }
  
}
