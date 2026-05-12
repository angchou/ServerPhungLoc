package com.example.phungloc.services;

import com.example.phungloc.dto.request.product_request.CreateProductRequest;
import com.example.phungloc.dto.request.product_request.CreateProductTypeRequest;
import com.example.phungloc.dto.response.product_response.ProductResponse;
import com.example.phungloc.dto.response.product_response.ProductTypeResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    List<ProductTypeResponse> getProductTypes();
    void createProductType(CreateProductTypeRequest request);
    boolean updateProductType(String maLoai, CreateProductTypeRequest request);
    void deleteProductType(String maLoai);

    void createProduct(CreateProductRequest request);
    boolean updateProduct(String maSanPham, CreateProductRequest request);
    void disableProduct(String maSanPham);
    void enableProduct(String maSanPham);
    List<ProductResponse> getEnabledProducts();
    List<ProductResponse> getDisabledProducts();
}
