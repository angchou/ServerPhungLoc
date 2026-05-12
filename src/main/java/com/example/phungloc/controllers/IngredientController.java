package com.example.phungloc.controllers;

import com.example.phungloc.annonation.ValidMaNcc;
import com.example.phungloc.annonation.ValidMaNguyenLieu;
import com.example.phungloc.dto.request.ingredient_request.CreateIngredientRequest;
import com.example.phungloc.dto.response.ingredient_response.IngredientResponse;
import com.example.phungloc.exception.AppException;
import com.example.phungloc.exception.ErrorCode;
import com.example.phungloc.impl.IngredientServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/ingredient")
public class IngredientController {

    @Autowired
    private IngredientServiceImpl ingredientService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('REGION_MANAGER')")
    public ResponseEntity<?> createIngredient(@RequestBody @Valid CreateIngredientRequest request) {
        ingredientService.createIngredient(request);
        return ResponseEntity.ok(Map.of(
                "message", "Thành công tạo nguyên liệu mới!"
        ));
    }

    @PatchMapping("/update/{maNguyenLieu}")
    @PreAuthorize("hasRole('REGION_MANAGER')")
    public ResponseEntity<?> updateIngredient(@PathVariable @ValidMaNguyenLieu String maNguyenLieu, @RequestBody @Valid CreateIngredientRequest request) {
        boolean isUpdated = ingredientService.updateIngredient(maNguyenLieu, request);
        if (!isUpdated) {
            throw new AppException(ErrorCode.NOTHING_CHANGE, "Không có gì thay đổi so với hiện tại!");
        }
        return ResponseEntity.ok(Map.of(
                "message", "Đã cập nhật thông tin của nguyên liệu!"
        ));
    }

    @PatchMapping("/disable/{maNguyenLieu}")
    @PreAuthorize("hasRole('REGION_MANAGER')")
    public ResponseEntity<?> disableIngredient(@PathVariable @ValidMaNcc String maNguyenLieu) {
        ingredientService.disableIngredient(maNguyenLieu);
        return ResponseEntity.ok(Map.of(
                "message", "Đã cho ngừng sử dụng nguyên liệu!"
        ));
    }

    @PatchMapping("/enable/{maNguyenLieu}")
    @PreAuthorize("hasRole('REGION_MANAGER')")
    public ResponseEntity<?> enableIngredient(@PathVariable @ValidMaNcc String maNguyenLieu) {
        ingredientService.enableIngredient(maNguyenLieu);
        return ResponseEntity.ok(Map.of(
                "message", "Đã cho sử dụng nguyên liệu!"
        ));
    }

    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('WAREHOUSE', 'REGION_MANAGER')")
    public List<IngredientResponse> getIngredients() {
        return ingredientService.getIngredients();
    }

    @GetMapping("/get/supplier/{maNcc}")
    @PreAuthorize("hasRole('REGION_MANAGER')")
    public List<IngredientResponse> getIngredientsOfSupplier(@PathVariable @ValidMaNcc String maNcc) {
        return ingredientService.getIngredientsOfSupplier(maNcc);
    }

    @GetMapping("/get/enabled")
    @PreAuthorize("hasAnyRole('WAREHOUSE', 'REGION_MANAGER')")
    public List<IngredientResponse> getEnabledIngredients() {
        return ingredientService.getEnabledIngredients();
    }

    @GetMapping("/get/disabled")
    @PreAuthorize("hasAnyRole('REGION_MANAGER')")
    public List<IngredientResponse> getDisabledIngredients() {
        return ingredientService.getDisabledIngredients();
    }
}
