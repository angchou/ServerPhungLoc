package com.example.phungloc.controllers;

import com.example.phungloc.dto.request.AddProductRequest;
import com.example.phungloc.dto.request.DeleteProductRequest;
import com.example.phungloc.dto.request.MenuResponse;
import com.example.phungloc.impl.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    @Autowired
    private MenuServiceImpl menuService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<?> addProduct(@RequestBody AddProductRequest request) {
        return menuService.addProductMenu(request);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<?> deleteProduct(@RequestBody DeleteProductRequest request) {
        return menuService.deleteProductMenu(request);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public List<MenuResponse> viewMenu() {
        return menuService.viewMenu();
    }
}
