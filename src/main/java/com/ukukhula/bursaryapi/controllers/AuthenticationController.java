package com.ukukhula.bursaryapi.controllers;

import com.ukukhula.bursaryapi.services.AuthenticationService;

import net.minidev.json.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin("*")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
      
        this.authenticationService=authenticationService;
      
    }
  
   
@PostMapping("/Oauth/login")
public ResponseEntity<?> oAuthlogin(@RequestBody String oauthToken) {
    try {
        String email = "";
        Pattern pattern = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b");
        Matcher matcher = pattern.matcher(oauthToken);
        if (matcher.find()) {
            email = matcher.group();
        }
       

        // Call the signin method to get the authentication token and user role
        Map<String, String> signinResult = authenticationService.signin(email);

        // Extract the authentication token and user role from the result map
        String authToken = signinResult.get("jwt");
        String userRole = signinResult.get("userRole");
        String userId = signinResult.get("userId");
        

        // Create a JSON object
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("email", email);
        jsonResponse.put("token", authToken);
        jsonResponse.put("userRole", userRole);
        jsonResponse.put("userId", userId);

        // Return the JSON string as ResponseEntity
        return ResponseEntity.ok(jsonResponse.toString());
    } catch (Exception e) {
        // e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to authenticate user.");
    }
}
}
