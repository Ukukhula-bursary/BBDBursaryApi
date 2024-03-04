package com.ukukhula.bursaryapi.entities.Dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ukukhula.bursaryapi.entities.Request.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class jwtUser implements UserDetails {
    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private int contactId;
    private int roleId;
    private String password;
    private Role role;
    private boolean IsActiveUser;
    public jwtUser(int userID,String firstName, String lastName, String email, int contactId, int roleId, boolean IsActiveUser) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactId = contactId;
        this.roleId = roleId;
        this.IsActiveUser = IsActiveUser;

    
        switch (roleId) {
            case 2:
                this.role = Role.ROLE_BBDAdmin_Finance;
                break;
            case 3:
                this.role = Role.ROLE_BBDAdmin_Reviewers;
                break;
            case 1:
                this.role = Role.ROLE_BBDSuperAdmin;
                break;
            case 5:
                this.role = Role.ROLE_HOD;
                break;
            case 4:
                this.role = Role.ROLE_Student;
                break;
            case 6:
                this.role = Role.ROLE_UniversityAdmin;
                break;
            default:
                this.role = Role.ROLE_Student; 
                break;
        }
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
             return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public String getPassword() {
       return password;
    }
    @Override
    public String getUsername() {
       return email;
    }
    @Override
    public boolean isAccountNonExpired() {
       return true;
    }
    @Override
    public boolean isAccountNonLocked() {
       return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
       return true;
    }
    @Override
    public boolean isEnabled() {
       if(isIsActiveUser()){
           return true;
       }else{
           return false;
       }
    }}