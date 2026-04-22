package com.example.phungloc.services;

import com.example.phungloc.dto.request.AddProductRequest;
import com.example.phungloc.dto.request.DeleteProductRequest;
import com.example.phungloc.dto.request.MenuResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MenuService {
    ResponseEntity<?> addProductMenu(AddProductRequest request);
    ResponseEntity<?> deleteProductMenu(DeleteProductRequest request);
    List<MenuResponse> viewMenu();
}
