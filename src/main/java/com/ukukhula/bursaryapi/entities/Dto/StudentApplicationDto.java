package com.ukukhula.bursaryapi.entities.Dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentApplicationDto {
    private int universityID;
    private int applicationID;
    private int studentId;
    private String university;
    private String studentName;
    private String ethinity;
    private String status;
    private String motivation;
    private BigDecimal bursaryAmount;
    private String date;
    private String reviewer;
    private String reviewerComment;
}
