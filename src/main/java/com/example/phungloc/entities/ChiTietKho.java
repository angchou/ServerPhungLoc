package com.example.phungloc.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ChiTietKho")
public class ChiTietKho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maTonKho")
    private String maTonKho;
    @Column(name = "soLuong")
    private BigDecimal soLuong;

    @ManyToOne
    @JoinColumn(name = "maKho")
    private Kho kho;
    @ManyToOne
    @JoinColumn(name = "maNguyenLieu")
    private NguyenLieu nguyenLieu;

    public String getMaTonKho() {
        return maTonKho;
    }

    public void setMaTonKho(String maTonKho) {
        this.maTonKho = maTonKho;
    }

    public BigDecimal getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(BigDecimal soLuong) {
        this.soLuong = soLuong;
    }

    public Kho getKho() {
        return kho;
    }

    public void setKho(Kho kho) {
        this.kho = kho;
    }

    public NguyenLieu getNguyenLieu() {
        return nguyenLieu;
    }

    public void setNguyenLieu(NguyenLieu nguyenLieu) {
        this.nguyenLieu = nguyenLieu;
    }
}
