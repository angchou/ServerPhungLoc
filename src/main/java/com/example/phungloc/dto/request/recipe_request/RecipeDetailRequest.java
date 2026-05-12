package com.example.phungloc.dto.request.recipe_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RecipeDetailRequest {
    private String maDinhMuc;
    @NotBlank(message = "Mã nguyên liệu đang có giá trị null!")
    private String maNguyenLieu;
    @Positive(message = "Số lượng định mức phải lớn hơn 0!")
    private BigDecimal soLuong;
}
