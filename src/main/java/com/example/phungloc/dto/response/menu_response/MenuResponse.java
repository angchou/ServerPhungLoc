package com.example.phungloc.dto.response.menu_response;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class MenuResponse {
    private String maCtMenu;
    private String maSanPham;
    private String tenSanPham;
    private String moTa;
    private BigDecimal giaBan;
    private String hinhAnh;
    private String maLoai;
    private String tenLoai;
    private Integer trangThai;
    private List<SizeResponse> sizes;

    public MenuResponse(String maCtMenu, String maSanPham, String tenSanPham, String moTa, BigDecimal giaBan, String hinhAnh, String maLoai, String tenLoai, Integer trangThai) {
        this.maCtMenu = maCtMenu;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.moTa = moTa;
        this.giaBan = giaBan;
        this.hinhAnh = hinhAnh;
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.trangThai = trangThai;
    }

    public MenuResponse(String maCtMenu, String maSanPham, String tenSanPham, String moTa, BigDecimal giaBan, String hinhAnh, String maLoai, String tenLoai, Integer trangThai, List<SizeResponse> sizes) {
        this.maCtMenu = maCtMenu;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.moTa = moTa;
        this.giaBan = giaBan;
        this.hinhAnh = hinhAnh;
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.trangThai = trangThai;
        this.sizes = sizes;
    }

}
