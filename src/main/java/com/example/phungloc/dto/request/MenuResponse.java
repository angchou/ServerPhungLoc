package com.example.phungloc.dto.request;

import java.math.BigDecimal;

public class MenuResponse {
    private String maCtMenu;
    private String maSanPham;
    private String tenSanPham;
    private String moTa;
    private BigDecimal giaBan;
    private String hinhAnh;
    private String maLoai;
    private Integer trangThai;

    public MenuResponse(String maCtMenu, String maSanPham, String tenSanPham, String moTa, BigDecimal giaBan, String hinhAnh, String maLoai, Integer trangThai) {
        this.maCtMenu = maCtMenu;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.moTa = moTa;
        this.giaBan = giaBan;
        this.hinhAnh = hinhAnh;
        this.maLoai = maLoai;
        this.trangThai = trangThai;
    }

    public String getMaCtMenu() {
        return maCtMenu;
    }

    public void setMaCtMenu(String maCtMenu) {
        this.maCtMenu = maCtMenu;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }
}
