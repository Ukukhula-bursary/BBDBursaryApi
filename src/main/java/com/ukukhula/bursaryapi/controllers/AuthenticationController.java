package com.ukukhula.bursaryapi.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ukukhula.bursaryapi.entities.LoginResponse;
import com.ukukhula.bursaryapi.entities.User;
import com.ukukhula.bursaryapi.security.JwtIssuer;

import com.ukukhula.bursaryapi.services.UserRoleService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthenticationController {

    private final JwtIssuer jwtIssuer;
    private final UserRoleService userRoleService;

    public AuthenticationController(JwtIssuer jwtIssuer, UserRoleService userRoleService) {
        this.jwtIssuer = jwtIssuer;
        this.userRoleService = userRoleService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated User userDetails) {
//        System.out.println(userDetails);

        var token = jwtIssuer.issue(1, userDetails.getFirstName(),
                List.of("USER"));
        return LoginResponse.builder().accessToken(token)
                .build();
    }
  
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> oAuthlogin(@RequestBody String oauthToken) {
        // Assuming you receive the OAuth token as part of the request body
        System.out.println(oauthToken);
        // Validate and process the OAuth token
        // DecodedJWT decodedJWT = decodeOAuthToken(oauthToken);
        // if (decodedJWT != null) {
            // If token is valid, extract user email and issue a JWT token
            // String userEmail = decodedJWT.getClaim("email").asString();
            // var token = jwtIssuer.issue(1, userEmail, List.of("USER"));
            LoginResponse response = LoginResponse.builder().accessToken(oauthToken).build();
            return ResponseEntity.ok(response);
        // }
        // Handle invalid token
        // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
