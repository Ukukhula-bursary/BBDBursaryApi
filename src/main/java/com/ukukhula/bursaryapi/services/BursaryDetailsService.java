package com.ukukhula.bursaryapi.services;

import com.ukukhula.bursaryapi.entities.BursaryDetails;
import com.ukukhula.bursaryapi.entities.Department;
import com.ukukhula.bursaryapi.repositories.BursaryDetailsRepository;
import com.ukukhula.bursaryapi.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class BursaryDetailsService {
    @Autowired
    public BursaryDetailsRepository bursaryDetailsRepository;

    public BursaryDetailsService(BursaryDetailsRepository bursaryDetailsRepository) {
        this.bursaryDetailsRepository = bursaryDetailsRepository;
    }
    
    public BursaryDetails getBursaryDetailsByYear(int year) {
        return bursaryDetailsRepository.getBursaryDetailsByYear(year);
    }

    public BigDecimal evenlyDistributeBursary(int year) {
        return bursaryDetailsRepository.evenlyDistributeBursary(year);
    }
}
