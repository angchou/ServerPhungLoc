package com.example.phungloc.security;

import com.example.phungloc.auth.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Component

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());

    @Autowired
    private JwtUtil jwt;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        // no token
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {

            Claims claims = jwt.getClaims(token);

            String email = claims.getSubject();
            String role = claims.get("role", String.class);

            logger.info("Role: " + role);

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            List.of(authority)
                    );

            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            System.out.println("Exception");
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
