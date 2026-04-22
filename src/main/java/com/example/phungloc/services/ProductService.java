package com.example.phungloc.services;

import com.example.phungloc.dto.request.CreateProductRequest;
import com.example.phungloc.dto.response.ProductResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    String createProduct(CreateProductRequest request);
    ResponseEntity<?> updateProduct(String maSanPham, CreateProductRequest request);
    ResponseEntity<?> deleteProduct(String maSanPham);
    List<ProductResponse> getProduct(String maChiNhanh);
}
