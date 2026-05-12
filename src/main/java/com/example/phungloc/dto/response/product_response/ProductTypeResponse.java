package com.example.phungloc.dto.response.product_response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductTypeResponse {
    private String maLoai;
    private String tenLoai;
    private Integer soLuong;

    public ProductTypeResponse(String maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public ProductTypeResponse(String maLoai, String tenLoai, Integer soLuong) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.soLuong = soLuong;
    }

}
