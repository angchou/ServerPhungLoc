package com.example.phungloc.dto.request;

public class MenuResponse {
    private String maCtMenu;
    private String maSanPham;
    private String tenSanPham;
    private String moTa;
    private String maLoai;
    private Integer trangThai;

    public MenuResponse(String maCtMenu, String maSanPham, String tenSanPham, String moTa, String maLoai, Integer trangThai) {
        this.maCtMenu = maCtMenu;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.moTa = moTa;
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
