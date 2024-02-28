package com.ukukhula.bursaryapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ukukhula.bursaryapi.entities.Ethnicity;
import com.ukukhula.bursaryapi.services.EthnicityService;

@CrossOrigin("*")
@RestController
@RestControllerAdvice
@RequestMapping("/Ethnicity")
public class EthnicityController {

    private EthnicityService service;

    public EthnicityController(EthnicityService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Ethnicity>> getByAll() {
        return ResponseEntity.ok(service.getAll());
    }

}
