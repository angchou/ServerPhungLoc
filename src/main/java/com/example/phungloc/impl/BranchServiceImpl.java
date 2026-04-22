package com.example.phungloc.impl;

import com.example.phungloc.dto.request.CreateBranchRequest;
import com.example.phungloc.dto.response.BranchResponse;
import com.example.phungloc.entities.ChiNhanh;
import com.example.phungloc.entities.UserRole;
import com.example.phungloc.repositories.ChiNhanhRepo;
import com.example.phungloc.repositories.UserRoleRepo;
import com.example.phungloc.services.BranchService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private ChiNhanhRepo chiNhanhRepo;
    @Autowired
    private UserRoleRepo userRoleRepo;

    @Override
    @Transactional
    public ResponseEntity<?> createBranch(CreateBranchRequest request) {
        // create a new branch
        ChiNhanh chiNhanh = new ChiNhanh();
        chiNhanh.setTenChiNhanh(request.getTenChiNhanh());
        chiNhanh.setDiaChi(request.getDiaChi());
        // save
        chiNhanhRepo.save(chiNhanh);
        return ResponseEntity.ok().body("Successfully created branch");
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateBranch(String maChiNhanh, CreateBranchRequest request) {
        // find branch
        ChiNhanh chiNhanh = chiNhanhRepo.findById(maChiNhanh)
                .orElseThrow(() -> new RuntimeException("Branch doesn't exist!"));

        // check status
        if (chiNhanh.getTrangThai() == 2) {
            return ResponseEntity.status(404).body("This branch is already inactive!");
        }
        // check tenChiNhanh
        if (!chiNhanh.getTenChiNhanh().equals(request.getTenChiNhanh())) {
            chiNhanh.setTenChiNhanh(request.getTenChiNhanh());
        }
        // check diaChi
        if (!chiNhanh.getDiaChi().equals(request.getDiaChi())) {
            chiNhanh.setDiaChi(request.getDiaChi());
        }

        return ResponseEntity.ok().body("Successfully updated branch");
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteBranch(String maChiNhanh) {
        // find branch
        ChiNhanh chiNhanh = chiNhanhRepo.findById(maChiNhanh)
                .orElseThrow(() -> new RuntimeException("Branch doesn't exist!"));

        // check status
        if (chiNhanh.getTrangThai() == 2) {
            return ResponseEntity.status(404).body("This branch is already inactive!");
        }
        // update status
        chiNhanh.setTrangThai(2);

        return ResponseEntity.ok().body("Successfully inactivated branch");
    }

    @Override
    public List<BranchResponse> getBranches() {
        // region manager can see inactive branches
        // manager, warehouse can not see inactive branches
        // get maNhanvien
        String maNhanVien = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        // find user_role
        UserRole userRole = userRoleRepo.findByNhanVien_MaNhanVien(maNhanVien);

        // create list
        List<ChiNhanh> danhSachChiNhanh = List.of();
        // check
        if (userRole.getRole().getRoleName().toUpperCase().equals("REGION_MANAGER")) {
            danhSachChiNhanh = chiNhanhRepo.findByTrangThaiIn(List.of(1, 2));
        }
        else if (userRole.getRole().getRoleName().toUpperCase().equals("MANAGER") || userRole.getRole().getRoleName().toUpperCase().equals("WAREHOUSE")) {
            danhSachChiNhanh = chiNhanhRepo.findByTrangThaiIn(List.of(1));
        }
        
        // mapping and return
        return danhSachChiNhanh.stream().map(
                chiNhanh -> new BranchResponse(
                        chiNhanh.getMaChiNhanh(),
                        chiNhanh.getTenChiNhanh(),
                        chiNhanh.getDiaChi(),
                        chiNhanh.getTrangThai(),
                        chiNhanh.getNgayHoatDong()
                )
        ).toList();
    }

}
