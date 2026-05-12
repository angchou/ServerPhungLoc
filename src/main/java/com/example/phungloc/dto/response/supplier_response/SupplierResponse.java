package com.example.phungloc.dto.response.supplier_response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SupplierResponse {
    private String maNcc;
    private String tenNcc;
    private String sdt;
    private String diaChi;

    public SupplierResponse(String maNcc, String tenNcc, String sdt, String diaChi) {
        this.maNcc = maNcc;
        this.tenNcc = tenNcc;
        this.sdt = sdt;
        this.diaChi = diaChi;
    }

}
