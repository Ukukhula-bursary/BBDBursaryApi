package com.ukukhula.bursaryapi.entities;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UniversityApplication {
    private int UniversityApplicationID;
    private int UniversityID;
    private String Motivation;
    private int StatusID;
    private String date;
    private int ReviewerID_UserID;
    private String ReviewerComment;

}
