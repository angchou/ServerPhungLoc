package com.example.phungloc.dto.request.product_request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateProductTypeRequest {
    @NotBlank(message = "Tên loại sản phẩm không được để trống!")
    private String tenLoai;
}
