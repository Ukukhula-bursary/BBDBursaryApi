package com.ukukhula.bursaryapi.entities;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class UniversityAllocation {
    private int universityAllocationID;
    private int universityId;
    private BigDecimal amount;
    private int bursaryDetailsId;

    public UniversityAllocation(int universityAllocationID, int universityId, BigDecimal amount, int bursaryDetailsId) {
        this.universityAllocationID = universityAllocationID;
        this.universityId = universityId;
        this.bursaryDetailsId = bursaryDetailsId;
        this.amount = amount;

    }

}
