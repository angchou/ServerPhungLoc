package com.example.phungloc.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "chiNhanh")
public class ChiNhanh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maChiNhanh")
    private String maChiNhanh;
    @Column(name = "tenChiNhanh")
    private String tenChiNhanh;
    @Column(name = "diaChi")
    private String diaChi;
    @Column(name = "ngayHoatDong")
    private LocalDate ngayHoatDong;

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

    public LocalDate getNgayHoatDong() {
        return ngayHoatDong;
    }

    public void setNgayHoatDong(LocalDate ngayHoatDong) {
        this.ngayHoatDong = ngayHoatDong;
    }
}
