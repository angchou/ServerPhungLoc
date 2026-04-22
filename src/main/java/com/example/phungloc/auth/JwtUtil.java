package com.example.phungloc.auth;

import com.example.phungloc.entities.NhanVien;
import com.example.phungloc.entities.UserRole;
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

    private static final String SECRET_KEY = "PhungLoc/super_secret_key/chaugiaan_chauhaiham_lephongnha_nguyenthanhkhang/2026@UITers";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(NhanVien nhanVien, UserRole userRole) {
        String role = userRole.getRole().getRoleName();

        return Jwts.builder()
                .setSubject(nhanVien.getMaNhanVien())
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}