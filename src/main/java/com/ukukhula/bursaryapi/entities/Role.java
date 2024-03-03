package com.ukukhula.bursaryapi.entities;

import lombok.Data;

@Data
public class Role {
    private int roleId;
    private String role;


    public Role(int roleId, String role) {
        this.roleId = roleId;
        this.role = role;
    }
}
