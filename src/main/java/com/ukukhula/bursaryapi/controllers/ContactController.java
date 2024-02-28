package com.ukukhula.bursaryapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ukukhula.bursaryapi.entities.Contact;
import com.ukukhula.bursaryapi.services.ContactService;

@RestController
@CrossOrigin("*")
@RequestMapping("/Contact")
public class ContactController {
    private ContactService service;

    public ContactController(ContactService service)
    {
        this.service=service;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getById(int id)
    {
        return ResponseEntity.ok(service.getById(id));
    }
    
}
