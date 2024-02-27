package com.ukukhula.bursaryapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
      private static final Map<Integer, String> ROLE_MAP = new HashMap<>();

    static {
        ROLE_MAP.put(2, "BBDAdmin_Finance");
        ROLE_MAP.put(3, "BBDAdmin_Reviewers");
        ROLE_MAP.put(1, "BBDSuperAdmin");
        ROLE_MAP.put(5, "HOD");
        ROLE_MAP.put(4, "Student");
        ROLE_MAP.put(6, "UniversityAdmin");
    }

    public static String getRole(int roleId) {
        return ROLE_MAP.getOrDefault(roleId, "Unknown Role");
    }

    public String issue(int userId, String email)
     {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.HOURS)))
                .withClaim("e", getRole(userId))
                .withClaim("a", getRole(userId))
                .sign(Algorithm.HMAC256(SecurityConstants.SECRET_KEY));
    }
}
