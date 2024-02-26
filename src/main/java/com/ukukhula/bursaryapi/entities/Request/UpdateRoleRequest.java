package com.ukukhula.bursaryapi.entities.Request;
import lombok.Data;

@Data
public class  UpdateRoleRequest {
    private String Institution;
    private int RoleID;
    private String Email;
 
    public UpdateRoleRequest(int RoleID ,String email) {
        this.Email=email;
        this.RoleID = RoleID;
    }

}

