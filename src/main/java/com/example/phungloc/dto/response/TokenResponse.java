package com.example.phungloc.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenResponse {
    private String token;
    private String roleName;
    private String maChiNhanh;

    public TokenResponse(String token, String roleName, String maChiNhanh) {
        this.token = token;
        this.roleName = roleName;
        this.maChiNhanh = maChiNhanh;
    }

}
