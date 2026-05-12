package com.example.phungloc.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "PhieuNhapKho")
public class PhieuNhapKho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maPhieuNhap")
    private String maPhieuNhap;
    @Column(name = "ngayTao")
    private LocalDate ngayTao;
    @Column(name = "triGia")
    private BigDecimal triGia;

    @ManyToOne
    @JoinColumn(name = "maNhanVien")
    private NhanVien nhanVien;
    @ManyToOne
    @JoinColumn(name = "maKho")
    private Kho kho;

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public Kho getKho() {
        return kho;
    }

    public void setKho(Kho kho) {
        this.kho = kho;
    }

    public BigDecimal getTriGia() {
        return triGia;
    }

    public void setTriGia(BigDecimal triGia) {
        this.triGia = triGia;
    }
}
