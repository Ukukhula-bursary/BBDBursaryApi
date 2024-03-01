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
      
        ObjectMapper mapper = new ObjectMapper();
       System.out.println(oauthToken);
      
        JsonNode rootNode=null;
        try {
            rootNode = mapper.readTree(oauthToken);
        } catch (JsonMappingException e) {
          
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (JsonProcessingException e) {
         
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if(rootNode==null)
        {

        }
      
        String emailAddress = rootNode.get("user").get("emailAddress").asText();
        Optional<User> user= userService.getUserByEmail(emailAddress);

        if(user.isPresent())
        {
            String token=jwtIssuer.issue(user.get().getRoleId(), emailAddress);
            return ResponseEntity.ok(token);

        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // System.out.println(emailAddress);
       
    //    System.out.println(oauthToken.get("emailAddress"));
    // {"user":{"kind":"drive#user","displayName":"Sir MAKHOBA",
    // "photoLink":"https:\/\/lh3.googleusercontent.com\/a\/ACg8ocKp8TEuTc06c-cbuoZZTLy3v1uIkDCOZv9N9OBz6kRp=s64"
    // ,"me":true,"permissionId":"16354109988529483724","emailAddress":"manyoba1997@gmail.com"}}
    
            // LoginResponse response = LoginResponse.builder().accessToken(oauthToken.toString()).build();
            // return ResponseEntity.ok(response);
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
