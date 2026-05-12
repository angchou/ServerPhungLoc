package com.example.phungloc.dto.response.branch_response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class BranchResponse {
    private String maChiNhanh;
    private String tenChiNhanh;
    private String diaChi;
    private Integer trangThai;
    private LocalDate ngayHoatDong;

    public BranchResponse(String maChiNhanh, String tenChiNhanh, String diaChi, Integer trangThai, LocalDate ngayHoatDong) {
        this.maChiNhanh = maChiNhanh;
        this.tenChiNhanh = tenChiNhanh;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
        this.ngayHoatDong = ngayHoatDong;
    }

}
