package com.example.phungloc.dto.request.emp_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class CreateEmployeeRequest {
    @NotBlank(message = "Tên nhân viên không được để trống!")
    private String tenNhanVien;
    @NotBlank(message = "Tên tài khoản không được để trống!")
    @Size(min = 6, max = 50, message = "Tài khoản phải có tối thiểu 6 ký tự và tối đa 50 ký tự")
    private String taiKhoan;
    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 50, message = "Mật khẩu phải có tối thiểu 6 ký tự và tối đa 50 ký tự")
    private String matKhau;
    private LocalDate ngayLamViec;
    @NotBlank(message = "Mã chi nhánh đang có giá trị null!")
    private String maChiNhanh;
    @NotBlank(message = "Mã vai trò đang có giá trị null!")
    private String roleId;
}
