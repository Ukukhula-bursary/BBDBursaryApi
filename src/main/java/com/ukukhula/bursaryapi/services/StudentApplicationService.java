package com.ukukhula.bursaryapi.services;

import com.ukukhula.bursaryapi.entities.StudentApplication;
import com.ukukhula.bursaryapi.entities.Request.StudentApplicationRequest;
import com.ukukhula.bursaryapi.entities.Request.UpdateStudentApplicationRequest;
import com.ukukhula.bursaryapi.repositories.StudentApplicationRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StudentApplicationService {

    private final StudentApplicationRepository studentApplicationRepository;

    public StudentApplicationService(StudentApplicationRepository studentApplicationRepository) {
        this.studentApplicationRepository = studentApplicationRepository;
    }

    public StudentApplication findByStudentID(int studentID) {
        return studentApplicationRepository.findByStudentID(studentID);
    }

    public List<StudentApplication> getAllStudentsApplications() {
        return studentApplicationRepository.getAllStudentsApplications();
    }

    public Integer updateStudentsApplicationStatus(int studentID, String status) {
        return studentApplicationRepository.updateStudentsApplicationStatus(studentID, status);
    }

    public Integer updateStudentsApplicationColumnValue(int studentID, String columnName, String value) {
        return studentApplicationRepository.updateStudentsApplicationColumnValue(studentID, columnName, value);
    }

    public int createApplication(StudentApplicationRequest student) {
        return studentApplicationRepository.createApplication(student);
     
    }

    public int updateApplication(UpdateStudentApplicationRequest student) {
        return studentApplicationRepository.updateApplication(student);
    }
}
