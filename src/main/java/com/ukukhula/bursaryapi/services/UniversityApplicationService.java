package com.ukukhula.bursaryapi.services;


import com.ukukhula.bursaryapi.entities.UniversityApplication;
import com.ukukhula.bursaryapi.entities.Dto.UniversityApplicationDto;
import com.ukukhula.bursaryapi.repositories.UniversityApplicationRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UniversityApplicationService   {
    final
    UniversityApplicationRepository universityApplicationRepository;

    public UniversityApplicationService(UniversityApplicationRepository universityApplicationRepository) {
        this.universityApplicationRepository = universityApplicationRepository;
    }


 
    public UniversityApplication getApplicationByUniversityId(int universityId) {
        return universityApplicationRepository.getApplicationByUniversityId(universityId);
    }



    public List<UniversityApplicationDto> getAll() {
       return universityApplicationRepository.getUniversityApplicationFormated();
    }

    



}
