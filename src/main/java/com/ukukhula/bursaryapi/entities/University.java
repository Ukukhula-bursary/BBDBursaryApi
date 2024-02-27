package com.ukukhula.bursaryapi.entities;

import lombok.Data;

@Data
public class University {


  private int universityId;
  private String universityName;
  private int isActiveRecipient;

  public University(String name) {
    this.universityName = name;
    this.isActiveRecipient = 0;
  }



  public University(int universityId, String universityName, int isActiveRecipient) {
    this.universityId = universityId;
    this.universityName = universityName;
    this.isActiveRecipient = isActiveRecipient;
  }

  public String getUniversityName() {
    return universityName;
  }
}
