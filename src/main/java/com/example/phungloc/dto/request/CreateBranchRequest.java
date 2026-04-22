package com.example.phungloc.dto.request;

public class CreateBranchRequest {
    private String tenChiNhanh;
    private String diaChi;

    public CreateBranchRequest(String tenChiNhanh, String diaChi) {
        this.tenChiNhanh = tenChiNhanh;
        this.diaChi = diaChi;
    }

    public String getTenChiNhanh() {
        return tenChiNhanh;
    }

    public void setTenChiNhanh(String tenChiNhanh) {
        this.tenChiNhanh = tenChiNhanh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
