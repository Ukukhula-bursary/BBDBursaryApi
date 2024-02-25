package com.ukukhula.bursaryapi.entities;

import lombok.Data;

@Data
public class Contact {
    private int contactId;
    private String PhoneNumber;
    private String Email;
    public Contact(String phonenumber, String Email)
    {
        this.Email=Email;
        this.PhoneNumber=phonenumber;
        
    }
    
}
