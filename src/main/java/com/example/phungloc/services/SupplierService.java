package com.example.phungloc.services;

import com.example.phungloc.dto.request.supplier_request.CreateSupplierRequest;
import com.example.phungloc.dto.response.supplier_response.SupplierResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SupplierService {
    List<SupplierResponse> getSuppliers();
    void createSupplier(CreateSupplierRequest request);
    void deleteSupplier(String maNcc);
    boolean updateSupplier(String maNcc, CreateSupplierRequest request);
}
