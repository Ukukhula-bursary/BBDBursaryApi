package com.ukukhula.bursaryapi.entities;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentApplication {
    private int studentApplicationID;
    private int studentID;
    private String motivation;
    private BigDecimal bursaryAmount;
    private int statusID;
    private int reviewer_UserID;
    private String reviewerComment;
    private Date date;
    private int universityStaffID;
    private int bursaryDetailsID;


}