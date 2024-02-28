package com.ukukhula.bursaryapi.services;

import com.ukukhula.bursaryapi.entities.User;
import com.ukukhula.bursaryapi.entities.UserRole;
import com.ukukhula.bursaryapi.repositories.UserRoleRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    UserRoleRepository userRoleRepository;
    public UserRoleService(UserRoleRepository userRoleRepository)
    {
        this.userRoleRepository=userRoleRepository;
    }
    public String getByRoleId(int roleId)
    {
        return userRoleRepository.findRoleById(roleId);

    }
    public List<UserRole> getAll()
    {
        return userRoleRepository.findAll();
    }
    // public String getUserRole(int roleId)
    // {

    // }
}
