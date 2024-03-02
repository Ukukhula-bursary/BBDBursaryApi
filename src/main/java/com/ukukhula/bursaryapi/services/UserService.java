package com.ukukhula.bursaryapi.services;

import com.ukukhula.bursaryapi.entities.User;
import com.ukukhula.bursaryapi.entities.Request.UpdateRoleRequest;
import com.ukukhula.bursaryapi.entities.Request.UserRequest;
import com.ukukhula.bursaryapi.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
  public UserDetailsService userDetailsService() {
      return new UserDetailsService() {
          @Override
          public UserDetails loadUserByUsername(String username) {
            Optional<User> user= userRepository.getUserByEmail(username);
            if (user.isPresent()) {
                return userRepository.createJwtUser(user.get(), username);
                
            }
            throw new UsernameNotFoundException("User not found");
          }
      };
  }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public Boolean userExists(String email) {
        return userRepository.userExists(email);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
   

    public int save(UserRequest user) {
        return userRepository.addUser(user);
    }

    public int update(UserRequest user) {
        return userRepository.updateUser(user);
    }

    public boolean UpdateRole(UpdateRoleRequest role) {
        return userRepository.UpdateRole(role.getEmail(), role.getRoleID());
    }

}
