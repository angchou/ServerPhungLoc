package com.example.phungloc.dto.response.recipe_response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class RecipeResponse {
    private String maCongThuc;
    private LocalDate ngayTao;
    private BigDecimal giaUpsize;
    private List<RecipeDetailResponse> danhSachChiTiet;

    public RecipeResponse(String maCongThuc, LocalDate ngayTao,
                          BigDecimal giaUpsize,
                          List<RecipeDetailResponse> danhSachChiTiet) {
        this.maCongThuc = maCongThuc;
        this.ngayTao = ngayTao;
        this.giaUpsize = giaUpsize;
        this.danhSachChiTiet = danhSachChiTiet;
    }
}
