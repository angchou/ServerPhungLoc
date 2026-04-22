package com.example.phungloc.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "DinhMuc")
public class DinhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maDinhMuc")
    private String maDinhMuc;
    @Column(name = "soLuong")
    private BigDecimal soLuong;

    @ManyToOne
    @JoinColumn(name = "maCongThuc")
    private CongThuc congThuc;
    @ManyToOne
    @JoinColumn(name = "maNguyenLieu")
    private NguyenLieu nguyenLieu;

    public String getMaDinhMuc() {
        return maDinhMuc;
    }

    public void setMaDinhMuc(String maDinhMuc) {
        this.maDinhMuc = maDinhMuc;
    }

    public BigDecimal getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(BigDecimal soLuong) {
        this.soLuong = soLuong;
    }

    public CongThuc getCongThuc() {
        return congThuc;
    }

    public void setCongThuc(CongThuc congThuc) {
        this.congThuc = congThuc;
    }

    public NguyenLieu getNguyenLieu() {
        return nguyenLieu;
    }

    public void setNguyenLieu(NguyenLieu nguyenLieu) {
        this.nguyenLieu = nguyenLieu;
    }
}
