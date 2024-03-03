package com.ukukhula.bursaryapi.entities;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;

import lombok.Data;

@Data
public class StudentApplicationDetails_ActiveStudent {

    private int studentID;

    // Student Applications table
    private String motivation;
    private BigDecimal bursaryAmount;
    private int universityStaffID;


    public StudentApplicationDetails_ActiveStudent(int studentID, String motivation, BigDecimal bursaryAmount, int universityStaffID) {
        this.studentID = studentID;
        this.motivation = motivation;
        this.bursaryAmount = bursaryAmount;
        this.universityStaffID = universityStaffID;
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
