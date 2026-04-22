package com.example.phungloc.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "anchou_super_secret_key_anchou_super_secret_key_2026";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

//    public String generateToken(Users user, List<UserRole> userRoles) {
//        List<String> roles = userRoles
//                .stream()
//                .map(userRole -> userRole.getRole().getRoleName())
//                .toList();
//
//        return Jwts.builder()
//                .setSubject(user.getUserID())
//                .claim("roles", roles)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}