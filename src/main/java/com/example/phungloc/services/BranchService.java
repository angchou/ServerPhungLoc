package com.example.phungloc.services;

import com.example.phungloc.dto.request.branch_request.CreateBranchRequest;
import com.example.phungloc.dto.response.branch_response.BranchResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BranchService {
    void createBranch(CreateBranchRequest request);
    boolean updateBranch(String maChiNhanh, CreateBranchRequest request);
    void openBranch(String maChiNhanh);
    void closeBranch(String maChiNhanh);
    List<BranchResponse> getBranches();
}
