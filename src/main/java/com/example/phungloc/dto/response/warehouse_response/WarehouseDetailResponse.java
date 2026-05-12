package com.example.phungloc.dto.response.warehouse_response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class WarehouseDetailResponse {
    private String maTonKho;
    private BigDecimal soLuong;
    private String maNguyenLieu;
    private String tenNguyenLieu;
    private String donVi;
    private BigDecimal giaBan;
    private Integer trangThai;
    private String maNcc;
    private String tenNcc;
    private String sdt;
    private String diaChi;

    public WarehouseDetailResponse(String maTonKho, BigDecimal soLuong, String maNguyenLieu, String tenNguyenLieu, String donVi, BigDecimal giaBan, Integer trangThai, String maNcc, String tenNcc, String sdt, String diaChi) {
        this.maTonKho = maTonKho;
        this.soLuong = soLuong;
        this.maNguyenLieu = maNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.donVi = donVi;
        this.giaBan = giaBan;
        this.trangThai = trangThai;
        this.maNcc = maNcc;
        this.tenNcc = tenNcc;
        this.sdt = sdt;
        this.diaChi = diaChi;
    }

}