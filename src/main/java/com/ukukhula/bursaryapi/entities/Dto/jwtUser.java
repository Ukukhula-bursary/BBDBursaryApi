package com.ukukhula.bursaryapi.entities.Dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class jwtUser implements UserDetails {
    private String firstName;
    private String lastName;
    private String email;
    private int contactId;
    private int roleId;
    private boolean IsActiveUser;
    public jwtUser(String firstName, String lastName, String email, int roleId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roleId = roleId;
        switch (roleId) {
            case 2:
                this.role = Role.BBDAdmin_Finance;
                break;
            case 3:
                this.role = Role.BBDAdmin_Reviewers;
                break;
            case 1:
                this.role = Role.BBDSuperAdmin;
                break;
            case 5:
                this.role = Role.HOD;
                break;
            case 4:
                this.role = Role.Student;
                break;
            case 6:
                this.role = Role.UniversityAdmin;
                break;
            default:
                // Set a default role if the userRoleId doesn't match any defined role
                this.role = Role.Student; // or any other default role you prefer
                break;
        }
    
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }
    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPassword'");
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