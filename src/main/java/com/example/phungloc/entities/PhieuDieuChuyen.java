package com.example.phungloc.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "PhieuDieuChuyen")
public class PhieuDieuChuyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maDieuChuyen")
    private String maDieuChuyen;
    @Column(name = "ngayTao")
    private LocalDate ngayTao;

    @ManyToOne
    @JoinColumn(name = "maNhanVien")
    private NhanVien nhanVien;
    @ManyToOne
    @JoinColumn(name = "chiNhanhGui", referencedColumnName = "maChiNhanh")
    private ChiNhanh chiNhanhGui;
    @ManyToOne
    @JoinColumn(name = "chiNhanhNhan", referencedColumnName = "maChiNhanh")
    private ChiNhanh chiNhanhNhan;

    public String getMaDieuChuyen() {
        return maDieuChuyen;
    }

    public void setMaDieuChuyen(String maDieuChuyen) {
        this.maDieuChuyen = maDieuChuyen;
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

    public ChiNhanh getChiNhanhGui() {
        return chiNhanhGui;
    }

    public void setChiNhanhGui(ChiNhanh chiNhanhGui) {
        this.chiNhanhGui = chiNhanhGui;
    }

    public ChiNhanh getChiNhanhNhan() {
        return chiNhanhNhan;
    }

    public void setChiNhanhNhan(ChiNhanh chiNhanhNhan) {
        this.chiNhanhNhan = chiNhanhNhan;
    }
}
