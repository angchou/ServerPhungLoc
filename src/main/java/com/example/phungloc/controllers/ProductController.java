package com.example.phungloc.controllers;

import com.example.phungloc.annonation.ValidMaLoai;
import com.example.phungloc.annonation.ValidMaSanPham;
import com.example.phungloc.dto.request.product_request.CreateProductRequest;
import com.example.phungloc.dto.request.product_request.CreateProductTypeRequest;
import com.example.phungloc.dto.response.product_response.ProductResponse;
import com.example.phungloc.dto.response.product_response.ProductTypeResponse;
import com.example.phungloc.exception.AppException;
import com.example.phungloc.exception.ErrorCode;
import com.example.phungloc.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/type/get")
    @PreAuthorize("hasAnyRole('CASHIER', 'MANAGER', 'REGION_MANAGER')")
    public List<ProductTypeResponse> getProductTypes() {
        return productService.getProductTypes();
    }

    @PostMapping("/type/create")
    @PreAuthorize("hasAnyRole('REGION_MANAGER')")
    public ResponseEntity<?> createProductType(@RequestBody @Valid CreateProductTypeRequest request) {
        productService.createProductType(request);
        return ResponseEntity.ok(Map.of(
                "message", "Thành công tạo loại sản phẩm mới!"
        ));
    }

    @PatchMapping("/type/update/{maLoai}")
    @PreAuthorize("hasAnyRole('REGION_MANAGER')")
    public ResponseEntity<?> upateProductType(@PathVariable @ValidMaLoai String maLoai, @RequestBody @Valid CreateProductTypeRequest request) {
        boolean isUpdated = productService.updateProductType(maLoai, request);
        if (!isUpdated) {
            throw new AppException(ErrorCode.NOTHING_CHANGE, "Không có gì thay đổi so với hiện tại!");
        }
        return ResponseEntity.ok(Map.of(
                "message", "Đã cập nhật thông tin loại sản phẩm " + maLoai
        ));
    }

    @DeleteMapping("/type/delete/{maLoai}")
    @PreAuthorize("hasAnyRole('REGION_MANAGER')")
    public ResponseEntity<?> deleteProductType(@PathVariable @ValidMaLoai String maLoai) {
        productService.deleteProductType(maLoai);
        return ResponseEntity.ok(Map.of(
                "message", "Đã xóa loại sản phẩm " + maLoai
        ));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('REGION_MANAGER')")
    public ResponseEntity<?> createProduct(@RequestBody @Valid CreateProductRequest request) {
        productService.createProduct(request);
        return ResponseEntity.ok(Map.of(
                "message", "Thành công tạo sản phẩm mới!"
        ));
    }

    @PatchMapping("/update/{maSanPham}")
    @PreAuthorize("hasAnyRole('REGION_MANAGER')")
    public ResponseEntity<?> updateProduct(@PathVariable @ValidMaSanPham String maSanPham, @RequestBody @Valid CreateProductRequest request) {
        boolean isUpdated = productService.updateProduct(maSanPham, request);
        if (!isUpdated) {
            throw new AppException(ErrorCode.NOTHING_CHANGE, "Không có gì thay đổi so với hiện tại!");
        }
        return ResponseEntity.ok(Map.of(
                "message", "Đã cập nhật thông tin sản phẩm " + maSanPham
        ));
    }

    @PatchMapping("disable/{maSanPham}")
    @PreAuthorize("hasAnyRole('REGION_MANAGER')")
    public ResponseEntity<?> disableProduct(@PathVariable @ValidMaSanPham String maSanPham) {
        productService.disableProduct(maSanPham);
        return ResponseEntity.ok(Map.of(
                "message", "Đã ngừng bán sản phẩm " + maSanPham
        ));
    }

    @PatchMapping("enable/{maSanPham}")
    @PreAuthorize("hasAnyRole('REGION_MANAGER')")
    public ResponseEntity<?> enableProduct(@PathVariable @ValidMaSanPham String maSanPham) {
        productService.enableProduct(maSanPham);
        return ResponseEntity.ok(Map.of(
                "message", "Đã mở bán sản phẩm " + maSanPham
        ));
    }

    @GetMapping("/get_enabled")
    @PreAuthorize("hasAnyRole('MANAGER', 'REGION_MANAGER')")
    public List<ProductResponse> getEnabledProducts() {
        return productService.getEnabledProducts();
    }

    @GetMapping("/get_disabled")
    @PreAuthorize("hasAnyRole('MANAGER', 'REGION_MANAGER')")
    public List<ProductResponse> getDisabledProducts() {
        return productService.getDisabledProducts();
    }
}
