package com.ukukhula.bursaryapi.services;

import com.ukukhula.bursaryapi.entities.Department;
import com.ukukhula.bursaryapi.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentService {
    @Autowired
    public DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    
    public Department getDepartmentById(int id) {
        return departmentRepository.getDepartmentById(id);
    }

    public Department getDepartmentIdByName(String name) {
        return departmentRepository.getDepartmentIdByName(name);
    }


    public List<Department> getAllDepartments() {
        return departmentRepository.getAllDepartments();
    }
}
