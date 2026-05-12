package com.example.phungloc.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddProductRequest {
    @NotBlank(message = "Mã sản phẩm đang có giá trị null!")
    private String maSanPham;

}