package com.ukukhula.bursaryapi.entities;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class BursaryDetails {
    private int bursaryDetailsId;
    private int year;
    private BigDecimal totalAmount;


    public BursaryDetails(int bursaryDetailsId, int year, BigDecimal totalAmount) {
        this.bursaryDetailsId = bursaryDetailsId;
        this.year = year;
        this.totalAmount = totalAmount;
    }


}
