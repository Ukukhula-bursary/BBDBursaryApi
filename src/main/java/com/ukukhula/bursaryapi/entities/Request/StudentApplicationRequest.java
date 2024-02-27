package com.ukukhula.bursaryapi.entities.Request;



import lombok.Data;




@Data
public class StudentApplicationRequest {

   private String FirstName;
   private String LastName;
   private String PhoneNumber;
   private String Email;
//    private String 
    private String motivation;
    private double bursaryAmount;
    private String IDNumber;
    private int ethnicityID;
    private String hodEmail;
   

    // Constructor
    public StudentApplicationRequest( String FirstName, String LastName,String motivation,
                                     String bursaryAmount, String Email,
                                     String PhoneNumber,
                                      String IDNumber, int ethnicityID,String hodEmail
                                     ) {
    
        this.motivation = motivation;
        this.bursaryAmount = Double.parseDouble(bursaryAmount);
        this.IDNumber = IDNumber;
        this.ethnicityID = ethnicityID;
        this.hodEmail=hodEmail;
        this.Email=Email;
        this.motivation=motivation;
    }

 
}


