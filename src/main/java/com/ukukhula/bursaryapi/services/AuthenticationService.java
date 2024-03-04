package com.ukukhula.bursaryapi.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ukukhula.bursaryapi.entities.User;
import com.ukukhula.bursaryapi.entities.Dto.jwtUser;
import com.ukukhula.bursaryapi.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {//implements AuthenticationProvider{
  private final UserRepository userRepository;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

public Map<String, String> signin(String email) {
    Map<String, String> response = new HashMap<>();

    // authenticationManager.authenticate(
    //     new UsernamePasswordAuthenticationToken(email,"null"));
    User user = userRepository.getUserByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
    jwtUser uJwtUser=userRepository.createJwtUser(user,email);
    uJwtUser.setPassword("null");
    System.out.println("User "+uJwtUser.getRole());
    String jwt = jwtService.generateToken(uJwtUser);

    // Add JWT token and user role to the response map
    response.put("jwt", jwt);
    response.put("userRole", uJwtUser.getRole().toString());
    response.put("userId", String.valueOf(uJwtUser.getUserID()));

    return response;
}

}
