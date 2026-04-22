package com.example.phungloc.controllers;

import com.example.phungloc.dto.request.CreateBranchRequest;
import com.example.phungloc.dto.response.BranchResponse;
import com.example.phungloc.impl.BranchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branch")
public class BranchController {
    @Autowired
    private BranchServiceImpl branchService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('REGION_MANAGER')")
    public ResponseEntity<?> createBranch(@RequestBody CreateBranchRequest request) {
        return branchService.createBranch(request);
    }

    @PatchMapping("/update/{maChiNhanh}")
    @PreAuthorize("hasRole('REGION_MANAGER')")
    public ResponseEntity<?> updateBranch(@PathVariable String maChiNhanh, @RequestBody CreateBranchRequest request) {
        return branchService.updateBranch(maChiNhanh, request);
    }

    @DeleteMapping("/delete/branch")
    @PreAuthorize("hasRole('REGION_MANAGER')")
    public ResponseEntity<?> deleteBranch(@PathVariable String maChiNhanh) {
        return branchService.deleteBranch(maChiNhanh);
    }

    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('WAREHOUSE', 'MANAGER', 'REGION_MANAGER')")
    public List<BranchResponse> getBranches() {
        System.out.println("Get");
        return branchService.getBranches();
    }
}
