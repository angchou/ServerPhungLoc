package com.example.phungloc.dto.response;

import java.time.LocalDate;

public class BranchResponse {
    private String maChiNhanh;
    private String tenChiNhanh;
    private String diaChi;
    private Integer trangThai;
    private LocalDate ngayHoatDong;

    public BranchResponse(String maChiNhanh, String tenChiNhanh, String diaChi, Integer trangThai, LocalDate ngayHoatDong) {
        this.maChiNhanh = maChiNhanh;
        this.tenChiNhanh = tenChiNhanh;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
        this.ngayHoatDong = ngayHoatDong;
    }

    public String getMaChiNhanh() {
        return maChiNhanh;
    }

    public void setMaChiNhanh(String maChiNhanh) {
        this.maChiNhanh = maChiNhanh;
    }

    public String getTenChiNhanh() {
        return tenChiNhanh;
    }

    public void setTenChiNhanh(String tenChiNhanh) {
        this.tenChiNhanh = tenChiNhanh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public LocalDate getNgayHoatDong() {
        return ngayHoatDong;
    }

    public void setNgayHoatDong(LocalDate ngayHoatDong) {
        this.ngayHoatDong = ngayHoatDong;
    }
}
