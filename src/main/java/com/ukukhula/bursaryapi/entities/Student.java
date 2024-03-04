package com.ukukhula.bursaryapi.entities;
import lombok.Data;

@Data
public class Student {
    private int studentId;
    private int userId;
    private String idNumber;
    private int ethnicityId;
    private int universityId;
    private int departmentId;

    

    public Student(int studentId, int userId, String idNumber, int ethnicityId, int universityId, int departmentId) {
        this.studentId = studentId;
        this.userId = userId;
        this.idNumber = idNumber;
        this.ethnicityId = ethnicityId;
        this.universityId = universityId;
        this.departmentId = departmentId;
    }

}
