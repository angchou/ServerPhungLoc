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
    private Integer soLuong;
    @Column(name = "giaSanPham")
    private BigDecimal giaSanPham;

    @ManyToOne
    @JoinColumn(name = "soHoaDon")
    private HoaDon hoaDon;
    @ManyToOne
    @JoinColumn(name = "maSanPham")
    private SanPham sanPham;
    @ManyToOne
    @JoinColumn(name = "maKichCo")
    private KichCo kichCo;

    public BigDecimal getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(BigDecimal giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public String getMaCTHD() {
        return maCTHD;
    }

    public void setMaCTHD(String maCTHD) {
        this.maCTHD = maCTHD;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
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

    public KichCo getKichCo() {
        return kichCo;
    }

    public void setKichCo(KichCo kichCo) {
        this.kichCo = kichCo;
    }
}
