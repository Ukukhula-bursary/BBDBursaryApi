package com.ukukhula.bursaryapi.entities.Dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentAllocationDto {
    private int ApplicationID;
    private String university;
    private BigDecimal budget;
    private String status;
    private String Motivation;
    private String Date;
    private String reviewerName;
    private String ReviewerComment;

    
}
