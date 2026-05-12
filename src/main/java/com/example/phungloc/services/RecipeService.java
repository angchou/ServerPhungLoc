package com.example.phungloc.services;

import com.example.phungloc.dto.request.recipe_request.CreateRecipeRequest;
import com.example.phungloc.dto.response.recipe_response.RecipeResponse;
import com.example.phungloc.dto.response.recipe_response.SizeResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RecipeService {
    List<SizeResponse> getSizes();
    RecipeResponse getRecipe(String maKichCo, String maSanPham);
    void createRecipe(CreateRecipeRequest request);
    boolean updateRecipe(String maCongThuc, CreateRecipeRequest request);
    void deleteRecipe(String maCongThuc);
}