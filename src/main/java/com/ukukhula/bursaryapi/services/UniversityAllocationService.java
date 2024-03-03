package com.ukukhula.bursaryapi.services;

import com.ukukhula.bursaryapi.entities.University;
import com.ukukhula.bursaryapi.entities.UniversityAllocation;
import com.ukukhula.bursaryapi.repositories.UniversityAllocationRepository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversityAllocationService {
    @Autowired
    private final UniversityAllocationRepository universityAllocationRepository;

    public UniversityAllocationService(UniversityAllocationRepository universityAllocationRepository) {
        this.universityAllocationRepository = universityAllocationRepository;
    }

    public List<UniversityAllocation> getAllUniversityAllocations() {
        return universityAllocationRepository.getAllUniversityAllocations();
    }

    public List<UniversityAllocation> getUniversityAllocationsForYear(int year) {
        return universityAllocationRepository.getUniversityAllocationsForYear(year);
    }

    public List<UniversityAllocation> getUniversityAllocationsForUniversity(String universityName) {
        return universityAllocationRepository.getUniversityAllocationsForUniversity(universityName);
    }

    public UniversityAllocation getUniversityAllocationsForUniversityYear(int year, String universityName) {
        return universityAllocationRepository.getUniversityAllocationsForUniversityYear(year, universityName);
    }

    public BigDecimal getRemainingAmountInFundForYear(int year, String universityName) {
        return universityAllocationRepository.getRemainingAmountInFundForYear(year, universityName);
    }


}