package com.example.phungloc.services;

import com.example.phungloc.dto.request.emp_request.CreateEmployeeRequest;
import com.example.phungloc.dto.response.emp_response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    // NOTE: Just for region_manager
    void createManager(CreateEmployeeRequest request);
    List<EmployeeResponse> getManagerOfBranch(String maChiNhanh);

    // NOTE: Just for manager
    void createEmployee(CreateEmployeeRequest request);
    List<EmployeeResponse> getEmployees();

    // NOTE: for both manger and region_manager
    boolean updateEmployee(String maNhanVien, CreateEmployeeRequest request);
    void activateEmployee(String maNhanVien);
    void deactivateEmployee(String maNhanVien);
}
