package com.example.phungloc.controllers;

import com.example.phungloc.dto.request.CreateProductRequest;
import com.example.phungloc.dto.response.ProductResponse;
import com.example.phungloc.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('REGION_MANAGER')")
    public String createProduct(@RequestBody CreateProductRequest request) {
        return productService.createProduct(request);
    }

    @PatchMapping("/update/{maSanPham}")
    @PreAuthorize("hasAnyRole('REGION_MANAGER')")
    public ResponseEntity<?> updateProduct(@PathVariable String maSanPham, @RequestBody CreateProductRequest request) {
        return productService.updateProduct(maSanPham, request);
    }

    @DeleteMapping("delete/{maSanPham}")
    @PreAuthorize("hasAnyRole('REGION_MANAGER')")
    public ResponseEntity<?> deleteProduct(@PathVariable String maSanPham) {
        return productService.deleteProduct(maSanPham);
    }

    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('MANAGER', 'REGION_MANAGER')")
    public List<ProductResponse> getProduct() {
        return productService.getProduct();
    }
}
