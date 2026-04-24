package com.example.phungloc.services;

import com.example.phungloc.dto.request.CreateIngredientRequest;
import com.example.phungloc.dto.response.IngredientResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IngredientService {
    ResponseEntity<?> createIngredient(CreateIngredientRequest request);
    ResponseEntity<?> updateIngredient(String maNguyenLieu, CreateIngredientRequest request);
    ResponseEntity<?> deleteIngredient(String maNguyenLieu);
    List<IngredientResponse> getIngredient();
}
