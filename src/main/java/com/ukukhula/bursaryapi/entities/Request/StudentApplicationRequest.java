package com.ukukhula.bursaryapi.entities.Request;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import com.ukukhula.bursaryapi.entities.Contact;
import com.ukukhula.bursaryapi.entities.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class StudentApplicationRequest {
 
   private String Name;
   private String LastName;
   private String PhoneNumber;
//    private String 
    private String motivation;
    private double bursaryAmount;
    private String IDNumber;
    private int ethnicityID;
    private int hodID;
   

    // Constructor
    public StudentApplicationRequest( String motivation,
                                     double bursaryAmount,
                                     String reviewerComment,
                                      String IDNumber
                                     ) {
    
        this.motivation = motivation;
        this.bursaryAmount = bursaryAmount;
  
 
        this.IDNumber = IDNumber;
        this.ethnicityID = ethnicityID;
    
    }

 
}


