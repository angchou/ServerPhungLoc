package com.example.phungloc.dto.request.ingredient_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CreateIngredientRequest {
    @NotBlank(message = "Tên nguyên liệu không được để trống!")
    private String tenNguyenLieu;
    @NotBlank(message = "Đơn vị không được để trống!")
    private String donVi;
    @PositiveOrZero(message = "Giá bán theo đơn vị phải lớn hơn hoặc bằng 0!")
    private BigDecimal giaBan;
    @NotBlank(message = "Vui lòng chọn nhà cung cấp!")
    private String maNcc;
}
