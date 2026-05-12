package com.example.phungloc.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    @NotBlank(message = "Tài khoản không được để trống!")
    private String taiKhoan;
    @NotBlank(message = "Mật khẩu không được để trống!")
    private String matKhau;

    public LoginRequest(String taiKhoan, String matKhau) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }
}
