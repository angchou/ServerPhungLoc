package com.example.phungloc.dto.request.branch_request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class CreateBranchRequest {
    @NotBlank(message = "Tên chi nhánh không được để trống!")
    private String tenChiNhanh;

    @NotBlank(message = "Địa chỉ không được để trống!")
    private String diaChi;
    private LocalDate ngayHoatDong;

}
