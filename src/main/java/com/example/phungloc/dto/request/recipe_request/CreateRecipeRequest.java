package com.example.phungloc.dto.request.recipe_request;

import com.example.phungloc.dto.response.recipe_response.RecipeDetailResponse;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class CreateRecipeRequest {
    @NotBlank(message = "Mã sản phẩm đang có giá trị null!")
    private String maSanPham;
    @NotBlank(message = "Mã kích cỡ đang có gí trị null!")
    private String maKichCo;
    @PositiveOrZero(message = "Giá upsize sản phẩm phải lớn hơn hoặc bằng 0!")
    private BigDecimal giaUpsize;
    @NotNull(message = "Danh sách định mức không được null!")
    @NotEmpty(message = "Danh sách định mức không được rỗng!")
    private List<RecipeDetailRequest> danhSachDinhMuc;
}
