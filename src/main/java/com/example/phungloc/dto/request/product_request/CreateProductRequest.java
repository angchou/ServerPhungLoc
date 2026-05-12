package com.example.phungloc.dto.request.product_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CreateProductRequest {
    @NotBlank(message = "Tên sản phẩm không được bỏ trống!")
    private String tenSanPham;
    private String moTa;
    @NotBlank(message = "Vui lòng chọn loại sản phẩm!")
    private String maLoai;
    @Positive(message = "Giá bán sản phẩm phải lớn hơn 0!")
    private BigDecimal giaBan;

}
