package com.example.phungloc.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RecipeProjection {
    String getMaCongThuc();
    LocalDate getNgayTao();
    BigDecimal getGiaUpsize();

    String getMaDinhMuc();
    BigDecimal getSoLuong();

    String getMaNguyenLieu();
    String getTenNguyenLieu();
    String getDonVi();
    BigDecimal getGiaBan();
    Integer getTrangThai();

    String getMaNcc();
    String getTenNcc();
}
