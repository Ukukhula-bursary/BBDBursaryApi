package com.ukukhula.bursaryapi.services;

import com.ukukhula.bursaryapi.entities.University;
import com.ukukhula.bursaryapi.repositories.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UniversityService {

  @Autowired
  final
  UniversityRepository universityRepository;

  public UniversityService(UniversityRepository universityRepository) {
    this.universityRepository = universityRepository;
  }


  public University addUniversity(University university) {
    Integer universityID = universityRepository.addUniversity(university);

    if (universityID == 0) {
      return null;
    }

    return universityRepository.getUniversityById(universityID);
  }

 
  public University getUniversityById(int id) {
    return universityRepository.getUniversityById(id);
  }

 
  public List<University> getAllUniversities() {
    return universityRepository.getAllUniversities();
  }
}
