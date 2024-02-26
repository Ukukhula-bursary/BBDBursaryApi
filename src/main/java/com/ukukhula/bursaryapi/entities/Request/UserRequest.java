package com.ukukhula.bursaryapi.entities.Request;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import com.ukukhula.bursaryapi.entities.Contact;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class  UserRequest {
    // private int id;
    private String firstName;
    private String lastName;
  
    private int userRoleId;
    // private int contactId;
    private String PhoneNumber;
    private String Email;
    private int IsActiveID;

    public UserRequest(String firstName, String lastName,String phoneNumber ,String email , int IsActiveID,int userRoleId) {
        // this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.Email=email;
        this.PhoneNumber=phoneNumber;
        this.IsActiveID = 1;
        this.userRoleId=userRoleId;
    }

}

