package com.example.phungloc.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "CongThuc")
public class CongThuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maCongThuc")
    private String maCongThuc;
    @Column(name = "ngayTao")
    private LocalDate ngayTao;
    @Column(name = "giaUpsize")
    private BigDecimal giaUpsize;

    @OneToMany(mappedBy = "congThuc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DinhMuc> danhSachDinhMuc;

    @ManyToOne
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

    public BigDecimal getGiaUpsize() {
        return giaUpsize;
    }

    public void setGiaUpsize(BigDecimal giaUpsize) {
        this.giaUpsize = giaUpsize;
    }

    public List<DinhMuc> getDanhSachDinhMuc() {
        return danhSachDinhMuc;
    }

    public void setDanhSachDinhMuc(List<DinhMuc> danhSachDinhMuc) {
        this.danhSachDinhMuc = danhSachDinhMuc;
    }
}
