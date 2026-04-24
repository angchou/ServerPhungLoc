package com.example.phungloc.controllers;

import com.example.phungloc.dto.request.CreateIngredientRequest;
import com.example.phungloc.dto.response.IngredientResponse;
import com.example.phungloc.entities.NguyenLieu;
import com.example.phungloc.impl.IngredientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {

    @Autowired
    private IngredientServiceImpl ingredientService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('WAREHOUSE')")
    public ResponseEntity<?> createIngredient(@RequestBody CreateIngredientRequest request) {
        return ingredientService.createIngredient(request);
    }

    @PatchMapping("/update/{maNguyenLieu}")
    @PreAuthorize("hasRole('WAREHOUSE')")
    public  ResponseEntity<?> updateIngredient(@PathVariable String maNguyenLieu, @RequestBody CreateIngredientRequest request) {
        return ingredientService.updateIngredient(maNguyenLieu, request);
    }

    @DeleteMapping("/delete/{maNguyenLieu}")
    @PreAuthorize("hasRole('WAREHOUSE')")
    public  ResponseEntity<?> deleteIngredient(@PathVariable String maNguyenLieu){
        return ingredientService.deleteIngredient(maNguyenLieu);
    }

    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('WAREHOUSE', 'REGION_MANAGER')")
    public List<IngredientResponse> getIngredient() {
        System.out.println("get");
        return ingredientService.getIngredient();
    }
}
