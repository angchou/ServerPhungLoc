package com.example.phungloc.dto.request.invoice_request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CreateInvoiceRequest {
    @NotBlank(message = "Không thấy mã UUID để đồng bộ hóa đơn!")
    private String uuid;
    @NotBlank(message = "Mã chi nhánh đang có giá trị null")
    private String maChiNhanh;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ngayTao;
    @PositiveOrZero(message = "Trị giá hóa đơn phải lớn hơn hoặc bằng 0!")
    private BigDecimal triGia;
    private String phuongThuc;
    @NotEmpty(message = "Chi tiết hóa đơn không được rỗng!")
    private List<InvoiceDetailRequest> chiTietHoaDon;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMaChiNhanh() {
        return maChiNhanh;
    }

    public void setMaChiNhanh(String maChiNhanh) {
        this.maChiNhanh = maChiNhanh;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public BigDecimal getTriGia() {
        return triGia;
    }

    public void setTriGia(BigDecimal triGia) {
        this.triGia = triGia;
    }

    public String getPhuongThuc() {
        return phuongThuc;
    }

    public void setPhuongThuc(String phuongThuc) {
        this.phuongThuc = phuongThuc;
    }

    public List<InvoiceDetailRequest> getChiTietHoaDon() {
        return chiTietHoaDon;
    }

    public void setChiTietHoaDon(List<InvoiceDetailRequest> chiTietHoaDon) {
        this.chiTietHoaDon = chiTietHoaDon;
    }
}
