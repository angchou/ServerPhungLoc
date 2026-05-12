package com.example.phungloc.services;

import com.example.phungloc.dto.request.warehouse_request.CreateStockReceiptDetailRequest;
import com.example.phungloc.dto.response.warehouse_response.WarehouseDetailResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WarehouseService {
    List<WarehouseDetailResponse> getWarehouseDetail();
    ResponseEntity<?> createStockReceipt(List<CreateStockReceiptDetailRequest> danhSach);
    ResponseEntity<?> createStockOutReceipt(String maChiNhanh, List<CreateStockReceiptDetailRequest> danhSach);
}
