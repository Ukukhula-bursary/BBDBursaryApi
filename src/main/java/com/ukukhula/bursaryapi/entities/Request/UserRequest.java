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
  
    // private int contactId;
    private String PhoneNumber;
    private String Email;
    private int IsActiveUser;
    private int RoleId;

    public UserRequest(String firstName, String lastName,String phoneNumber ,String email , int IsActiveUser,int roleId) {
        // this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.Email=email;
        this.PhoneNumber=phoneNumber;
        this.IsActiveUser = 1;
        this.RoleId=roleId;
    }

}

