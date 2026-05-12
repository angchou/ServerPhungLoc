package com.example.phungloc.controllers;

import com.example.phungloc.annonation.ValidMaNcc;
import com.example.phungloc.dto.request.supplier_request.CreateSupplierRequest;
import com.example.phungloc.dto.response.supplier_response.SupplierResponse;
import com.example.phungloc.exception.AppException;
import com.example.phungloc.exception.ErrorCode;
import com.example.phungloc.impl.SupplierServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    private SupplierServiceImpl supplierService;

    @GetMapping("/all")
    public List<SupplierResponse> getSuppliers() {
        return supplierService.getSuppliers();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSupplier(@RequestBody @Valid CreateSupplierRequest request) {
        supplierService.createSupplier(request);
        return ResponseEntity.ok(Map.of(
                "message", "Thành công tạo nhà cung cấp mới"
        ));
    }

    @DeleteMapping("/delete/{maNcc}")
    public ResponseEntity<?> deleteSupploer(@PathVariable @ValidMaNcc String maNcc) {
        supplierService.deleteSupplier(maNcc);
        return ResponseEntity.ok(Map.of(
                "message", "Thành công xóa nhà cung cấp"
        ));
    }

    @PatchMapping("/update/{maNcc}")
    public ResponseEntity<?> updateSupplier(@PathVariable @ValidMaNcc String maNcc, @RequestBody @Valid CreateSupplierRequest request) {
        boolean isUpdated = supplierService.updateSupplier(maNcc, request);
        if (isUpdated) {
            return ResponseEntity.ok(Map.of(
                    "message", "Đã cập nhật thông tin nhà cung cấp!"
            ));
        }
        throw new AppException(ErrorCode.NOTHING_CHANGE, "Không có thông tin nào thay đổi!");
    }

}
