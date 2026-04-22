package com.example.phungloc.auth;

import com.example.phungloc.dto.request.LoginRequest;
import com.example.phungloc.dto.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

}
