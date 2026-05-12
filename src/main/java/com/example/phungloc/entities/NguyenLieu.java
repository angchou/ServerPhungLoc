package com.example.phungloc.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class NguyenLieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maNguyenLieu")
    private String maNguyenLieu;
    @Column(name = "tenNguyenLieu")
    private String tenNguyenLieu;
    @Column(name = "donVi")
    private String donVi;
    @Column(name = "giaBan")
    private BigDecimal giaBan;
    @Column(name = "trangThai")
    private Integer trangThai;

    @ManyToOne
    @JoinColumn(name = "maNcc")
    private NhaCungCap nhaCungCap;

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

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }
}
