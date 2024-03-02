package com.ukukhula.bursaryapi.controllers;

import com.ukukhula.bursaryapi.entities.DataTransferObject.Statuses;
import com.ukukhula.bursaryapi.services.StatusesService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/statuses")
public class StatusesController {

  private StatusesService statusesService;

  public StatusesController(StatusesService statusesService) {
    this.statusesService = statusesService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<Statuses>> getAll() {
    List<Statuses> statuses = statusesService.getAll();
    return ResponseEntity.ok(statuses);
  }
}
