package com.example.phungloc.controllers;

import com.example.phungloc.annonation.ValidMaChiNhanh;
import com.example.phungloc.annonation.ValidMaNhanVien;
import com.example.phungloc.dto.request.emp_request.CreateEmployeeRequest;
import com.example.phungloc.dto.response.emp_response.EmployeeResponse;
import com.example.phungloc.exception.AppException;
import com.example.phungloc.exception.ErrorCode;
import com.example.phungloc.services.EmployeeService;
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
@RequestMapping("/api/emp")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/manager/create")
    @PreAuthorize("hasRole('REGION_MANAGER')")
    public ResponseEntity<?> createManager(@Valid @RequestBody CreateEmployeeRequest request) {
        employeeService.createManager(request);
        return ResponseEntity.ok(Map.of("message", "Đã tạo tài khoản quản lý thành công!"));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        employeeService.createEmployee(request);
        return ResponseEntity.ok(Map.of("message", "Đã tạo tài khoản nhân viên thành công!"));
    }

    @GetMapping("/get/manager/{maChiNhanh}")
    @PreAuthorize("hasRole('REGION_MANAGER')")
    public List<EmployeeResponse> getManagerOfBranch(@PathVariable @ValidMaChiNhanh String maChiNhanh) {
        return employeeService.getManagerOfBranch(maChiNhanh);
    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('MANAGER')")
    public List<EmployeeResponse> getEmployees() {
        return employeeService.getEmployees();
    }

    @PatchMapping("/update/{maNhanVien}")
    @PreAuthorize("hasAnyRole('MANAGER', 'REGION_MANAGER')")
    public ResponseEntity<?> updateEmployee(@PathVariable @ValidMaNhanVien String maNhanVien, @Valid @RequestBody CreateEmployeeRequest request) {
        boolean isUpdated = employeeService.updateEmployee(maNhanVien, request);
        if (isUpdated) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Cập nhật nhân viên thành công!"
            ));
        }
        throw new AppException(ErrorCode.NOTHING_CHANGE, "Không có gì thay đổi so với hiện tại!");
    }

    @PatchMapping("/activate/{maNhanVien}")
    @PreAuthorize("hasAnyRole('MANAGER', 'REGION_MANAGER')")
    public ResponseEntity<?> activateEmployee(@PathVariable @ValidMaNhanVien String maNhanVien) {
        employeeService.activateEmployee(maNhanVien);
        return ResponseEntity.ok(Map.of("message", "Đã cho nhân viên đi làm trở lại!"));
    }

    @PatchMapping("/deactivate/{maNhanVien}")
    @PreAuthorize("hasAnyRole('MANAGER', 'REGION_MANAGER')")
    public ResponseEntity<?> deactivateEmployee(@PathVariable @ValidMaNhanVien String maNhanVien) {
        employeeService.deactivateEmployee(maNhanVien);
        return ResponseEntity.ok(Map.of("message", "Đã cho nhân viên nghỉ việc!"));
    }

}
