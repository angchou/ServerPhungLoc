package com.example.phungloc.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "CongThuc")
public class CongThuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maCongThuc")
    private String maCongThuc;
    @Column(name = "ngayTao")
    private LocalDate ngayTao;

    @OneToOne
    @JoinColumn(name = "maSanPham")
    private SanPham sanPham;
    @ManyToOne
    @JoinColumn(name = "maKichCo")
    private KichCo kichCo;

    public String getMaCongThuc() {
        return maCongThuc;
    }

    public void setMaCongThuc(String maCongThuc) {
        this.maCongThuc = maCongThuc;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
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
