package com.ukukhula.bursaryapi.services;

import com.ukukhula.bursaryapi.entities.University;
import com.ukukhula.bursaryapi.repositories.UniversityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UniversityService {
  final
  UniversityRepository universityRepository;

  public UniversityService(UniversityRepository universityRepository) {
    this.universityRepository = universityRepository;
  }

 
  public University addUniversity(String name) {
    Integer id = universityRepository.addUniversity(name);
    return universityRepository.getUniversityById(id);
  }

 
  public University getUniversityById(int id) {
    return universityRepository.getUniversityById(id);
  }

 
  public List<University> getAllUniversities() {
    return universityRepository.getAllUniversities();
  }
}
