package com.example.phungloc.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "CTHD")
public class CTHD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maCthd")
    private String maCTHD;
    @Column(name = "soLuong")
    private BigDecimal soLuong;

    @ManyToOne
    @JoinColumn(name = "soHoaDon")
    private HoaDon hoaDon;
    @ManyToOne
    @JoinColumn(name = "maSanPham")
    private SanPham sanPham;

    public String getMaCTHD() {
        return maCTHD;
    }

    public void setMaCTHD(String maCTHD) {
        this.maCTHD = maCTHD;
    }

    public BigDecimal getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(BigDecimal soLuong) {
        this.soLuong = soLuong;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }
}
