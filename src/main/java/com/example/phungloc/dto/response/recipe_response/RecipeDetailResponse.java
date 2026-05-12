package com.example.phungloc.dto.response.recipe_response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RecipeDetailResponse {
    private String maDinhMuc;
    private BigDecimal soLuong;
    private String maNguyenLieu;
    private String tenNguyenLieu;
    private String donVi;
    private BigDecimal giaBan;
    private Integer trangThai;
    private String maNcc;
    private String tenNcc;

    public RecipeDetailResponse(String maDinhMuc, BigDecimal soLuong, String maNguyenLieu, String tenNguyenLieu, String donVi, BigDecimal giaBan, Integer trangThai, String maNcc, String tenNcc) {
        this.maDinhMuc = maDinhMuc;
        this.soLuong = soLuong;
        this.maNguyenLieu = maNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.donVi = donVi;
        this.giaBan = giaBan;
        this.trangThai = trangThai;
        this.maNcc = maNcc;
        this.tenNcc = tenNcc;
    }
}
