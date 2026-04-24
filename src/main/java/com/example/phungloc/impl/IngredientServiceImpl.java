package com.example.phungloc.impl;

import com.example.phungloc.dto.request.CreateIngredientRequest;
import com.example.phungloc.dto.response.IngredientResponse;
import com.example.phungloc.entities.NguyenLieu;
import com.example.phungloc.entities.UserRole;
import com.example.phungloc.repositories.NguyenLieuRepo;
import com.example.phungloc.repositories.UserRoleRepo;
import com.example.phungloc.services.IngredientService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private NguyenLieuRepo nguyenLieuRepo;
    @Autowired
    private UserRoleRepo userRoleRepo;

    @Override
    @Transactional
    public ResponseEntity<?> createIngredient(CreateIngredientRequest request) {
        // create a new Ingredient
        NguyenLieu nguyenLieu = new NguyenLieu();
        nguyenLieu.setTenNguyenLieu(request.getTenNguyenLieu());
        nguyenLieu.setMoTa(request.getMoTa());
        nguyenLieu.setTrangThai(1);

        // save
        nguyenLieuRepo.save(nguyenLieu);
        return ResponseEntity.ok().body("Successfully created ingredient!");
    }

    @Override
    @Transactional
    public  ResponseEntity<?> updateIngredient(String maNguyenLieu, CreateIngredientRequest request) {
        // find ingredient
        NguyenLieu nguyenLieu = nguyenLieuRepo.findById(maNguyenLieu)
                .orElseThrow(()-> new RuntimeException("Ingredient doesn't exist!"));

        // check status
        if (nguyenLieu.getTrangThai() == 2) {
            return ResponseEntity.status(404).body("This ingredient is already inactive!");
        }

        // check tenNguyenLieu
        if (!nguyenLieu.getTenNguyenLieu().equals(request.getTenNguyenLieu())) {
            nguyenLieu.setTenNguyenLieu(request.getTenNguyenLieu());
        }

        // check moTa
        if(!nguyenLieu.getMoTa().equals(request.getMoTa())) {
            nguyenLieu.setMoTa(request.getMoTa());
        }

        nguyenLieuRepo.save(nguyenLieu);
        return ResponseEntity.ok().body("Successfully updated ingredient");
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteIngredient(String maNguyenLieu) {
        // find ingredient
        NguyenLieu nguyenLieu = nguyenLieuRepo.findById(maNguyenLieu)
                .orElseThrow(()-> new RuntimeException("Ingredient doesn't exist!"));

        // check status
        if (nguyenLieu.getTrangThai() == 2) {
            return ResponseEntity.status(404).body("This ingredient is already inactive!");
        }

        // update status
        nguyenLieu.setTrangThai(2);

        //save
        nguyenLieuRepo.save(nguyenLieu);
        return ResponseEntity.ok().body("Successfully inactivated ingredient");
    }

    @Override
    public List<IngredientResponse> getIngredient() {
        //region manager can see inactive ingredient
        //warehouse manager can not see inactive ingredient
        // get maNhanVien
        String maNhanVien = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();

        // find user_role
        UserRole userRole = userRoleRepo.findByNhanVien_MaNhanVien(maNhanVien);

        // create list
        List<NguyenLieu> danhSachNguyenLieu = List.of();

        //check role
        if (userRole.getRole().getRoleName().toUpperCase().equals("REGION_MANAGER")) {
            danhSachNguyenLieu = nguyenLieuRepo.findByTrangThaiIn(List.of(1, 2));
        }
        else if (userRole.getRole().getRoleName().toUpperCase().equals("WAREHOUSE")) {
            danhSachNguyenLieu = nguyenLieuRepo.findByTrangThaiIn(List.of(1));
        }

        //mapping and return
        return danhSachNguyenLieu.stream().map(
                nguyenLieu -> new IngredientResponse(
                        nguyenLieu.getMaNguyenLieu(),
                        nguyenLieu.getTenNguyenLieu(),
                        nguyenLieu.getMoTa(),
                        nguyenLieu.getTrangThai()
                )
        ).toList();
    }
}
