package com.example.phungloc.services;

import com.example.phungloc.dto.request.ingredient_request.CreateIngredientRequest;
import com.example.phungloc.dto.response.ingredient_response.IngredientResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IngredientService {
    void createIngredient(CreateIngredientRequest request);
    boolean updateIngredient(String maNguyenLieu, CreateIngredientRequest request);
    void disableIngredient(String maNguyenLieu);
    void enableIngredient(String maNguyenLieu);

    List<IngredientResponse> getIngredients();
    List<IngredientResponse> getIngredientsOfSupplier(String maNcc);
    List<IngredientResponse> getEnabledIngredients();
    List<IngredientResponse> getDisabledIngredients();
}
