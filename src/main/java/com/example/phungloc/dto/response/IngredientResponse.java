package com.example.phungloc.dto.response;

public class IngredientResponse {
    private String maNguyenLieu;
    private String tenNguyenLieu;
    private String moTa;
    private  Integer trangThai;

    public IngredientResponse(String maNguyenLieu, String tenNguyenLieu, String moTa, Integer trangThai) {
        this.maNguyenLieu = maNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public String getMaNguyenLieu() {
        return maNguyenLieu;
    }

    public void setMaNguyenLieu(String maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }

    public String getTenNguyenLieu() {
        return tenNguyenLieu;
    }

    public void setTenNguyenLieu(String tenNguyenLieu) {
        this.tenNguyenLieu = tenNguyenLieu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }
}
