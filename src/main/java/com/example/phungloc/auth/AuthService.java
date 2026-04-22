package com.example.phungloc.auth;

import com.example.phungloc.dto.request.LoginRequest;
import com.example.phungloc.dto.response.TokenResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {
    TokenResponse login(@RequestBody LoginRequest request);
}
