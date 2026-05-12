package com.example.phungloc.controllers;

import com.example.phungloc.annonation.ValidMaCongThuc;
import com.example.phungloc.annonation.ValidMaKichCo;
import com.example.phungloc.annonation.ValidMaSanPham;
import com.example.phungloc.dto.request.recipe_request.CreateRecipeRequest;
import com.example.phungloc.dto.response.recipe_response.RecipeResponse;
import com.example.phungloc.dto.response.recipe_response.SizeResponse;
import com.example.phungloc.exception.AppException;
import com.example.phungloc.exception.ErrorCode;
import com.example.phungloc.services.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/size/all")
    public List<SizeResponse> getSizes () {
        return recipeService.getSizes();
    }

    @GetMapping("/get/{maSanPham}/{maKichCo}")
    public RecipeResponse getRecipe(@PathVariable @ValidMaKichCo String maKichCo, @PathVariable @ValidMaSanPham String maSanPham) {
        return recipeService.getRecipe(maKichCo, maSanPham);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('REGION_MANAGER')")
    public ResponseEntity<?> createRecipe(@RequestBody @Valid CreateRecipeRequest request) {
        recipeService.createRecipe(request);
        return ResponseEntity.ok(Map.of(
                "message", "Đã tạo công thức mới cho sản phẩm " + request.getMaSanPham()
        ));
    }

    @PatchMapping("/update/{maCongThuc}")
    @PreAuthorize("hasAnyRole('REGION_MANAGER')")
    public ResponseEntity<?> updateRecipe(@PathVariable @ValidMaCongThuc String maCongThuc, @RequestBody @Valid CreateRecipeRequest request) {
        boolean isUpdated = recipeService.updateRecipe(maCongThuc, request);
        if (!isUpdated) {
            throw new AppException(ErrorCode.NOTHING_CHANGE, "Không có gì thay đổi so với hiện tại!");
        }
        return ResponseEntity.ok(Map.of(
                "message", "Đã cập nhật chi tiết công thức " + maCongThuc
        ));
    }

    @DeleteMapping("/delete/{maCongThuc}")
    @PreAuthorize("hasAnyRole('REGION_MANAGER')")
    public ResponseEntity<?> deleteRecipe(@PathVariable @ValidMaCongThuc String maCongThuc) {
        recipeService.deleteRecipe(maCongThuc);
        return ResponseEntity.ok(Map.of(
                "message", "Đã xóa công thức " + maCongThuc
        ));
    }

}