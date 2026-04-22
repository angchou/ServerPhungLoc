package com.example.phungloc.services;

import com.example.phungloc.dto.request.CreateBranchRequest;
import com.example.phungloc.dto.response.BranchResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BranchService {
    ResponseEntity<?> createBranch(CreateBranchRequest request);
    ResponseEntity<?> updateBranch(String maChiNhanh, CreateBranchRequest request);
    ResponseEntity<?> deleteBranch(String maChiNhanh);
    List<BranchResponse> getBranches();
}
