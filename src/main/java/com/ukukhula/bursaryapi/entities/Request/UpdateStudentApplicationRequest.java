package com.ukukhula.bursaryapi.entities.Request;


import lombok.Data;

@Data
public class UpdateStudentApplicationRequest {

    private String FirstName;
    private String LastName;
    private String PhoneNumber;
    private String Email;
    private String motivation;
    private Double bursaryAmount;
    public int studentApplicationID;
    public int studentID;

  
    public UpdateStudentApplicationRequest(String FirstName, String LastName, String motivation,
            String bursaryAmount, String Email,
            String PhoneNumber, int studentApplicationID, int studentID) {

        this.FirstName = isNullOrEmpty(FirstName) ? null : FirstName;
        this.LastName = isNullOrEmpty(LastName) ? null : LastName;
        this.PhoneNumber = isNullOrEmpty(PhoneNumber) ? null : PhoneNumber;
        this.Email = isNullOrEmpty(Email) ? null : Email;
        this.motivation = isNullOrEmpty(motivation) ? null : motivation;
        this.bursaryAmount = isNullOrEmpty(bursaryAmount) ? null : Double.parseDouble(bursaryAmount);
        this.studentApplicationID = studentApplicationID;
        this.studentID = studentID;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
