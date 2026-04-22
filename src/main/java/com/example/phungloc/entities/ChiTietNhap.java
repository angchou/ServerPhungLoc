package com.example.phungloc.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ChiTietNhap")
public class ChiTietNhap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maChiTiet")
    private String maChiTiet;
    @Column(name = "soLuong")
    private BigDecimal soLuong;

    @ManyToOne
    @JoinColumn(name = "maPhieuNhap")
    private PhieuNhapKho phieuNhapKho;
    @ManyToOne
    @JoinColumn(name = "maNguyenLieu")
    private NguyenLieu nguyenLieu;

    public String getMaChiTiet() {
        return maChiTiet;
    }

    public void setMaChiTiet(String maChiTiet) {
        this.maChiTiet = maChiTiet;
    }

    public BigDecimal getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(BigDecimal soLuong) {
        this.soLuong = soLuong;
    }

    public PhieuNhapKho getPhieuNhapKho() {
        return phieuNhapKho;
    }

    public void setPhieuNhapKho(PhieuNhapKho phieuNhapKho) {
        this.phieuNhapKho = phieuNhapKho;
    }

    public NguyenLieu getNguyenLieu() {
        return nguyenLieu;
    }

    public void setNguyenLieu(NguyenLieu nguyenLieu) {
        this.nguyenLieu = nguyenLieu;
    }
}
