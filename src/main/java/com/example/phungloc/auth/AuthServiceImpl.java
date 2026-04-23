package com.example.phungloc.auth;

import com.example.phungloc.dto.request.LoginRequest;
import com.example.phungloc.dto.response.TokenResponse;
import com.example.phungloc.entities.NhanVien;
import com.example.phungloc.entities.UserRole;
import com.example.phungloc.repositories.NhanVienRepo;
import com.example.phungloc.repositories.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private NhanVienRepo nhanVienRepo;
    @Autowired
    private UserRoleRepo userRoleRepo;
    @Autowired
    private JwtUtil jwt;

    @Override
    public TokenResponse login(LoginRequest request) {
        NhanVien nhanVien = nhanVienRepo.findByTaiKhoan(request.getTaiKhoan())
                .orElseThrow(() -> new RuntimeException("Employee doesn't exist!"));

        // check password
        if (!nhanVien.getMatKhau().equals(request.getMatKhau())) {
            throw new RuntimeException("Password doesn't match!");
        }
        // get user's role
        UserRole userRole = userRoleRepo.findByNhanVien_MaNhanVien(nhanVien.getMaNhanVien());
        // get token
        String token = jwt.generateToken(nhanVien, userRole);
        // send token
        return new TokenResponse(token, userRole.getRole().getRoleName());
    }
}
