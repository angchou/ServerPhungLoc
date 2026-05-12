package com.example.phungloc.dto.request.invoice_request;

import java.math.BigDecimal;

public class InvoiceDetailRequest {
    private String maSanPham;
    private String maKichCo;
    private BigDecimal giaBan;
    private BigDecimal giaUpsize;
    private Integer soLuong;

    public BigDecimal getGiaUpsize() {
        return giaUpsize;
    }

    public void setGiaUpsize(BigDecimal giaUpsize) {
        this.giaUpsize = giaUpsize;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getMaKichCo() {
        return maKichCo;
    }

    public void setMaKichCo(String maKichCo) {
        this.maKichCo = maKichCo;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
}
