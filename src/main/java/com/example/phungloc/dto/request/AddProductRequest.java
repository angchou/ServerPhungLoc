package com.example.phungloc.dto.request;

public class AddProductRequest {
    private String maMenu;
    private String maSanPham;

    public String getMaMenu() {
        return maMenu;
    }

    public void setMaMenu(String maMenu) {
        this.maMenu = maMenu;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }
}
