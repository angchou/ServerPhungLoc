package com.example.phungloc.dto.response;

public class ProductResponse {
    private String maSanPham;
    private String tenSanPham;
    private String moTa;
    private String maMenu;
    private String maLoai;
    private Integer trangThai;

    public ProductResponse(String maSanPham, String tenSanPham, String moTa, String maMenu, String maLoai, Integer trangThai) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.moTa = moTa;
        this.maMenu = maMenu;
        this.maLoai = maLoai;
        this.trangThai = trangThai;
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

    public String getMaMenu() {
        return maMenu;
    }

    public void setMaMenu(String maMenu) {
        this.maMenu = maMenu;
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
