package com.example.phungloc.dto.request.warehouse_request;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CreateStockReceiptDetailRequest {
    private String maNguyenLieu;
    @PositiveOrZero(message = "Số lượng nguyên liệu tồn kho phải lớn hơn hoặc bằng 0!")
    private BigDecimal soLuong;

}
