package com.ukukhula.bursaryapi.services;

import com.ukukhula.bursaryapi.entities.Department;
import com.ukukhula.bursaryapi.entities.University;
import com.ukukhula.bursaryapi.entities.UniversityStaff;
import com.ukukhula.bursaryapi.entities.UniversityStaffDetails;
import com.ukukhula.bursaryapi.repositories.DepartmentRepository;
import com.ukukhula.bursaryapi.repositories.UniversityStaffRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UniversityStaffService {
    @Autowired
    public UniversityStaffRepository universityStaffRepository;

    public UniversityStaffService (UniversityStaffRepository universityStaffRepository) {
        this.universityStaffRepository = universityStaffRepository;
    }
    
    public UniversityStaff addUniversityStaff(UniversityStaffDetails universityStaffDetails) {
        int universityStaffID = universityStaffRepository.addUniversityStaff(universityStaffDetails);

        if (universityStaffID == 0) {
            return null;
        }

        return universityStaffRepository.getUniversityStaffById(universityStaffID);
    }

    public UniversityStaff getUniversityStaffById(int id) {
    return universityStaffRepository.getUniversityStaffById(id);
  }

 
  public List<UniversityStaff> getAllUniversityStaff() {
    return universityStaffRepository.getAllUniversityStaff();
  }
}
