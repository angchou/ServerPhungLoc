package com.example.phungloc.dto.request;

public class CreateProductRequest {
    private String tenSanPham;
    private String moTa;
    private String maMenu;
    private String maLoai;

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
}
