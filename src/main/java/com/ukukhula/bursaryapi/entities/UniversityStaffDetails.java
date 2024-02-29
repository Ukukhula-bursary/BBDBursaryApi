package com.ukukhula.bursaryapi.entities;

import lombok.Data;

@Data
public class UniversityStaffDetails {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String universityName;
    private String departmentName;
    // private int UniversityID;
    // private int DepartmentID;
    // private int RoleID;

    public UniversityStaffDetails(String firstName, String lastName, String phoneNumber, String email, String universityName, String departmentName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.universityName = universityName;
        this.departmentName = departmentName;

    }
}
