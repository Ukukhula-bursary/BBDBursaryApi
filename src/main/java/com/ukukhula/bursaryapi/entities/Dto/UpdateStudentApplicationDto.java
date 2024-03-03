package com.ukukhula.bursaryapi.entities.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateStudentApplicationDto {
  private int applicationID;
  private int statusID;
}
