package com.example.phungloc.entities;

import jakarta.persistence.*;

@Entity
public class NguyenLieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maNguyenLieu")
    private String maNguyenLieu;
    @Column(name = "tenNguyenLieu")
    private String tenNguyenLieu;
    @Column(name = "moTa")
    private String moTa;
    @Column(name = "trangThai")
    private Integer trangThai;

    public String getMaNguyenLieu() {
        return maNguyenLieu;
    }

    public void setMaNguyenLieu(String maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }

    public String getTenNguyenLieu() {
        return tenNguyenLieu;
    }

    public void setTenNguyenLieu(String tenNguyenLieu) {
        this.tenNguyenLieu = tenNguyenLieu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }
}
