package com.example.phungloc.controllers;

import com.example.phungloc.annonation.ValidMaChiNhanh;
import com.example.phungloc.dto.request.branch_request.CreateBranchRequest;
import com.example.phungloc.dto.response.branch_response.BranchResponse;
import com.example.phungloc.exception.AppException;
import com.example.phungloc.exception.ErrorCode;
import com.example.phungloc.impl.BranchServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/branch")
public class BranchController {
    @Autowired
    private BranchServiceImpl branchService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('REGION_MANAGER')")
    public ResponseEntity<?> createBranch(@Valid @RequestBody CreateBranchRequest request) {
        branchService.createBranch(request);
        return ResponseEntity.ok(Map.of(
                "message", "Tạo chi nhánh mới thành công!"
        ));
    }

    @PatchMapping("/update/{maChiNhanh}")
    @PreAuthorize("hasRole('REGION_MANAGER')")
    public ResponseEntity<?> updateBranch(@PathVariable @ValidMaChiNhanh String maChiNhanh, @Valid @RequestBody CreateBranchRequest request) {
        boolean isUpdated = branchService.updateBranch(maChiNhanh, request);
        if (isUpdated) {
            return ResponseEntity.ok(Map.of(
                    "message", "Đã cập nhật thông tin chi nhánh!"
            ));
        }
        throw new AppException(ErrorCode.NOTHING_CHANGE, "Không có thông tin nào thay đổi!");
    }

    @PatchMapping("/open/{maChiNhanh}")
    @PreAuthorize("hasRole('REGION_MANAGER')")
    public ResponseEntity<?> openBranch(@PathVariable @ValidMaChiNhanh String maChiNhanh) {
        branchService.openBranch(maChiNhanh);
        return ResponseEntity.ok(Map.of(
                "message", "Thành công mở cửa chi nhánh!"
        ));
    }

    @PatchMapping("/close/{maChiNhanh}")
    @PreAuthorize("hasRole('REGION_MANAGER')")
    public ResponseEntity<?> closeBranch(@PathVariable @ValidMaChiNhanh String maChiNhanh) {
        branchService.closeBranch(maChiNhanh);
        return ResponseEntity.ok(Map.of(
                "message", "Thành công đóng cửa chi nhánh!"
        ));
    }

    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('REGION_MANAGER')")
    public List<BranchResponse> getBranches() {
        return branchService.getBranches();
    }
}
