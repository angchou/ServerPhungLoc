package com.example.phungloc.controllers;

import com.example.phungloc.dto.request.warehouse_request.CreateStockReceiptDetailRequest;
import com.example.phungloc.dto.response.warehouse_response.WarehouseDetailResponse;
import com.example.phungloc.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('WAREHOUSE')")
    public List<WarehouseDetailResponse> getWarehouseDetail() {
        return warehouseService.getWarehouseDetail();
    }

    @PostMapping("/stock_in/create")
    @PreAuthorize("hasAnyRole('WAREHOUSE')")
    public ResponseEntity<?> createStockReceipt(@RequestBody List<CreateStockReceiptDetailRequest> danhSach) {
        return warehouseService.createStockReceipt(danhSach);
    }

    @PostMapping("/stock_out/{maChiNhanh}/create")
    @PreAuthorize("hasAnyRole('WAREHOUSE')")
    public ResponseEntity<?> createStockOutReceipt(@PathVariable String maChiNhanh, @RequestBody List<CreateStockReceiptDetailRequest> danhSach) {
        return warehouseService.createStockOutReceipt(maChiNhanh, danhSach);
    }

}
