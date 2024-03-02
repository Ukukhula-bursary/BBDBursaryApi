package com.ukukhula.bursaryapi.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.ukukhula.bursaryapi.entities.LoginResponse;
import com.ukukhula.bursaryapi.entities.User;
import com.ukukhula.bursaryapi.security.JwtIssuer;

import com.ukukhula.bursaryapi.services.UserRoleService;
import com.ukukhula.bursaryapi.services.UserService;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class AuthenticationController {

    private final JwtIssuer jwtIssuer;
    private UserService userService;


    public AuthenticationController(JwtIssuer jwtIssuer,UserService userService) {
        this.jwtIssuer = jwtIssuer;
        this.userService=userService;
      
    }

    // @PostMapping("/login")
    // public LoginResponse login(@RequestBody @Validated User userDetails) {

    //     var token = jwtIssuer.issue(1, userDetails.getFirstName(),
    //             List.of("USER"));
    //     return LoginResponse.builder().accessToken(token)
    //             .build();
    // }
  
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
//else {
            // If token is invalid, return unauthorized status
         //   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        //}
    //}

    // Method to decode and validate OAuth token
    private DecodedJWT decodeOAuthToken(String oauthToken) {
        // Decode and verify the token using your OAuth provider's method
        // Example: Using Auth0 JWT library for decoding
        try {
            return JWT.decode(oauthToken);
        } catch (JWTDecodeException e) {
            // Token decoding failed, handle error
            return null;
        }
    }
}
