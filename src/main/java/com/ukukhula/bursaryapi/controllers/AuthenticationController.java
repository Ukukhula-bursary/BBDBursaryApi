package com.ukukhula.bursaryapi.controllers;

import com.ukukhula.bursaryapi.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController

public class AuthenticationController {

    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
      
        this.authenticationService=authenticationService;
      
    }
  
    @PostMapping("/auth/login")
    public ResponseEntity<String> oAuthlogin(@RequestBody String oauthToken) {
      
        try {
            System.out.println(oauthToken);
            String email = "";
            Pattern pattern = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b");
            Matcher matcher = pattern.matcher(oauthToken);
            if (matcher.find()) {
                email = matcher.group();
            }
            System.out.println(email);

            return ResponseEntity.ok(authenticationService.signin(email));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to authenticate user.");
        }
    }
}
