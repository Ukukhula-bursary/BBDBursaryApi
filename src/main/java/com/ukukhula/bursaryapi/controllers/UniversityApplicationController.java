package com.ukukhula.bursaryapi.controllers;

// import com.ukukhula.bursaryapi.assemblers.UniversityApplicationModelAssembler;
import com.ukukhula.bursaryapi.entities.University;
import com.ukukhula.bursaryapi.entities.UniversityApplication;
import com.ukukhula.bursaryapi.entities.Dto.UniversityApplicationDto;
import com.ukukhula.bursaryapi.services.UniversityApplicationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// @CrossOrigin("*")
@RestController
@RequestMapping("/universities/application")
public class UniversityApplicationController {

    final
    UniversityApplicationService universityApplicationService;

    // private final UniversityApplicationModelAssembler assembler;


    public UniversityApplicationController(UniversityApplicationService universityApplicationService) {
        this.universityApplicationService = universityApplicationService;
        // this.assembler = assembler;
    }

    @GetMapping("/{id}")
    public UniversityApplication oneApplication(@PathVariable int id) {

        UniversityApplication universityApplication =
                universityApplicationService.getApplicationByUniversityId(id);
        return universityApplication;
    }
    @GetMapping("/all")
    public List<UniversityApplicationDto> allApplications()
    {
        return universityApplicationService.getAll();
    }
}
