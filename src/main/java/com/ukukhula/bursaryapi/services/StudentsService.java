package com.ukukhula.bursaryapi.services;

import com.ukukhula.bursaryapi.entities.Student;
import com.ukukhula.bursaryapi.repositories.StudentsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentsService {
    @Autowired
    public StudentsRepository studentsRepository;

    public StudentsService (StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    public Student getStudentById(int studentId) {
    return studentsRepository.getStudentById(studentId);
  }

  public List<Student> getStudentsByUniversityName(String universityName) {
    return studentsRepository.getStudentsByUniversityName(universityName);
  }

  public List<Student> getActiveStudentsByUniversityName(String universityName) {
    return studentsRepository.getActiveStudentsByUniversityName(universityName);
  }

  public List<Student> getAllStudents() {
    return studentsRepository.getAllStudents();
  }
}
