package com.example.phungloc.dto.response.menu_response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class SizeResponse {
    private String maKichCo;
    private String tenKichCo;
    private BigDecimal giaUpsize;

    public SizeResponse(String maKichCo, String tenKichCo, BigDecimal giaUpsize) {
        this.maKichCo = maKichCo;
        this.tenKichCo = tenKichCo;
        this.giaUpsize = giaUpsize;
    }

}
