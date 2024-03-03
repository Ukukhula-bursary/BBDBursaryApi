package com.ukukhula.bursaryapi.services;

import com.ukukhula.bursaryapi.entities.Role;
import com.ukukhula.bursaryapi.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService {
    @Autowired
    public RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    public Role getRoleById(int id) {
        return roleRepository.getRoleById(id);
    }

    public Role getRoleIdByName(String name) {
        return roleRepository.getRoleIdByName(name);
    }


    public List<Role> getAllRoles() {
        return roleRepository.getAllRoles();
    }
}
