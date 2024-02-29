package com.ukukhula.bursaryapi.entities;
import lombok.Data;

@Data
public class UniversityStaff {
    private int universityStaffId;
    private int userId;
    private int universityId;
    private int departmentId;

    public UniversityStaff(int universityStaffId, int userId, int universityId, int departmentId) {
        this.universityStaffId = universityStaffId;
        this.userId = userId;
        this.universityId = universityId;
        this.departmentId = departmentId;
    }

}
