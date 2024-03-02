package com.ukukhula.bursaryapi.entities.Dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UniversityApplicationDto {
    private int UniversityID;
    private int ApplicationID;
    private String University;
    private String Budget;
    private String status;
    private String Motivation;
    private String Date;
    private String reviewerName;
    private String ReviewerComment;
}
