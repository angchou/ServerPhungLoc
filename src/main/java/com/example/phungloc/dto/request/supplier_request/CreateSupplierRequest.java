package com.example.phungloc.dto.request.supplier_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateSupplierRequest {
    @NotBlank(message = "Tên nhà cung cấp không được để trống!")
    private String tenNcc;
    @NotBlank(message = "Số điện thoại liên lạc không được để trống!")
    @Pattern(regexp = "\\d+", message = "Số điện thoại chỉ được chứa chữ số!")
    private String sdt;
    @NotBlank(message = "Địa chỉ nhà cung cấp không được để trống!")
    private String diaChi;

}
