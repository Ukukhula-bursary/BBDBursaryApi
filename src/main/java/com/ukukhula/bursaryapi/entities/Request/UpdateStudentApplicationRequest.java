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
import lombok.Data;


@Data
public class UpdateStudentApplicationRequest {

   private String FirstName;
   private String LastName;
   private String PhoneNumber;
   private String Email;
//    private String 
    private String motivation;
    private double bursaryAmount;
    private String hodEmail;
   

    // Constructor 
    public UpdateStudentApplicationRequest( String FirstName, String LastName,String motivation,
                                     String bursaryAmount, String Email,
                                     String PhoneNumber,String hodEmail
                                     ) {
    
        this.motivation = motivation;
        this.bursaryAmount = Double.parseDouble(bursaryAmount);
    
        this.hodEmail=hodEmail;
        this.Email=Email;
    
        this.motivation=motivation;
    }

 
}


