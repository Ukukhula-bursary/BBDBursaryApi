package com.ukukhula.bursaryapi.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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


     public String signin(String email) {
    // authenticationManager.authenticate(
    //     new UsernamePasswordAuthenticationToken(email,"null"));
    User user = userRepository.getUserByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
    jwtUser uJwtUser=userRepository.createJwtUser(user,email);
    uJwtUser.setPassword("null");
    // system.out.println("User "+uJwtUser.);
    String jwt = jwtService.generateToken(uJwtUser);
    return jwt;
  }

    // @Override
    // public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    // }

    // @Override
    // public boolean supports(Class<?> authentication) {
    //           return authentication.equals(UsernamePasswordAuthenticationToken.class);
    // }
}
