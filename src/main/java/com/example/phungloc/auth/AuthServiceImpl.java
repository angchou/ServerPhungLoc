package com.example.phungloc.auth;

import com.example.phungloc.dto.request.LoginRequest;
import com.example.phungloc.dto.response.TokenResponse;
import com.example.phungloc.entities.ChiNhanh;
import com.example.phungloc.entities.NhanVien;
import com.example.phungloc.entities.UserRole;
import com.example.phungloc.repositories.NhanVienRepo;
import com.example.phungloc.repositories.UserRoleRepo;
import com.example.phungloc.exception.AppException;
import com.example.phungloc.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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
        if (nhanVien.getTrangThai() == 0) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Tài khoản đã bị vô hiệu hóa");
        }
        // get user's role
        UserRole userRole = userRoleRepo.findByNhanVien_MaNhanVien(nhanVien.getMaNhanVien())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy vai trò!"));
        // get token
        String token = jwt.generateToken(nhanVien, userRole);
        // send token
        String maChiNhanh = Optional.of(userRole)
                .map(UserRole::getNhanVien)
                .map(NhanVien::getChiNhanh)
                .map(ChiNhanh::getMaChiNhanh)
                .orElse(null);

        return new TokenResponse(token, userRole.getRole().getRoleName(), maChiNhanh);
    }
}
