package com.example.phungloc.dto.response.emp_response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class EmployeeResponse {
    private String maNhanVien;
    private String tenNhanVien;
    private String taiKhoan;
    private String matKhau;
    private LocalDate ngayLamViec;
    private int trangThai;
    private String maChiNhanh;
    private String tenChiNhanh;
    private String roleId;

    public EmployeeResponse(String maNhanVien, String tenNhanVien, String taiKhoan, String matKhau, LocalDate ngayLamViec, int trangThai, String maChiNhanh, String tenChiNhanh, String roleId) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.ngayLamViec = ngayLamViec;
        this.trangThai = trangThai;
        this.maChiNhanh = maChiNhanh;
        this.tenChiNhanh = tenChiNhanh;
        this.roleId = roleId;
    }

}