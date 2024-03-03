package com.ukukhula.bursaryapi.entities;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;

import lombok.Data;

@Data
public class StudentApplicationDetails_NewStudent {


    // User table
    private String firstName;
    private String lastName;
    // Contacts table
    private String phoneNumber;
    private String email;
    // Students table
    private String idNumber;
    private String ethnicity;
    private String universityName;
    private String departmentName;
	// Student Applications table
    private String motivation;
    private BigDecimal bursaryAmount;
    private int universityStaffID;



    public StudentApplicationDetails_NewStudent(String firstName, String lastName, String phoneNumber, String email, String idNumber, String ethnicity, String universityName, String departmentName, String motivation, BigDecimal bursaryAmount, int universityStaffID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.idNumber = idNumber;
        this.ethnicity = ethnicity;
        this.universityName = universityName;
        this.departmentName = departmentName;
        this.motivation = motivation;
        this.bursaryAmount = bursaryAmount;
        this.universityStaffID = universityStaffID;
    }

    
    public int getRoleID() {
        return 4;
    }

    public int getReviewer_UserID() {
        return 1;
    }

    public String getReviewerComment() {
        return "Your application has been submitted and is awaiting review.";
    }

    public LocalDate getDate() {
        return LocalDate.now();
    }

    public int getYear() {
        return getDate().getYear();
    }

    public int getStatusID() {
        return 1;
    }

}
