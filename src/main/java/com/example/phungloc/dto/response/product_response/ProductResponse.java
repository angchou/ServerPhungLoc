package com.example.phungloc.dto.response.product_response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductResponse {
    private String maSanPham;
    private String tenSanPham;
    private String moTa;
    private BigDecimal giaBan;
    private String maLoai;
    private Integer trangThai;
    private String hinhAnh;
    private String tenLoai;

    public ProductResponse(String maSanPham, String tenSanPham, String moTa, BigDecimal giaBan, String maLoai, Integer trangThai, String hinhAnh, String tenLoai) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.moTa = moTa;
        this.giaBan = giaBan;
        this.maLoai = maLoai;
        this.trangThai = trangThai;
        this.hinhAnh = hinhAnh;
        this.tenLoai = tenLoai;
    }

}
