package com.example.phungloc.impl;

import com.example.phungloc.dto.request.emp_request.CreateEmployeeRequest;
import com.example.phungloc.dto.response.emp_response.EmployeeResponse;
import com.example.phungloc.entities.ChiNhanh;
import com.example.phungloc.entities.NhanVien;
import com.example.phungloc.entities.Role;
import com.example.phungloc.entities.UserRole;
import com.example.phungloc.repositories.ChiNhanhRepo;
import com.example.phungloc.repositories.NhanVienRepo;
import com.example.phungloc.repositories.RoleRepo;
import com.example.phungloc.repositories.UserRoleRepo;
import com.example.phungloc.services.EmployeeService;
import com.example.phungloc.exception.AppException;
import com.example.phungloc.exception.ErrorCode;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.example.phungloc.util.UpdateHelper.updateIfChange;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    private NhanVienRepo nhanVienRepo;
    @Autowired
    private ChiNhanhRepo chiNhanhRepo;
    @Autowired
    private UserRoleRepo userRoleRepo;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    @Transactional
    public void createManager(CreateEmployeeRequest request) {
        log.info("Creating new Manager for: {}", request.getMaChiNhanh());
        NhanVien nhanVien = new NhanVien();

        if (nhanVienRepo.existsByTaiKhoan(request.getTaiKhoan())) {
            throw new AppException(ErrorCode.ACCOUNT_EXISTED, "Tài khoản này đã tồn tại!");
        }

        ChiNhanh chiNhanh = chiNhanhRepo.findById(request.getMaChiNhanh())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy chi nhánh: " + request.getMaChiNhanh()));

        // Create NHAN_VIEN
        nhanVien.setTenNhanVien(request.getTenNhanVien().trim().replaceAll("\\s+", " "));
        nhanVien.setTaiKhoan(request.getTaiKhoan().trim());
        nhanVien.setMatKhau(request.getMatKhau());

        // Sanitize ngayLamViec
        LocalDate ngayLam = (request.getNgayLamViec() == null) ? LocalDate.now() : request.getNgayLamViec();
        nhanVien.setNgayLamViec(ngayLam);

        nhanVien.setChiNhanh(chiNhanh);
        nhanVien.setTrangThai(1);
        nhanVienRepo.save(nhanVien);
        log.info("Successfully created new Manager {}", nhanVien.getMaNhanVien());

        // FIND ROLE: MANAGER R0003
        Role role = roleRepo.findById("R0003")
                .orElseThrow(() -> {
                    log.error("Missing role: {}", "R0003");
                    return new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy vai trò!");
                });

        // CREATE USER_ROLE
        UserRole userRole = new UserRole();
        userRole.setNhanVien(nhanVien);
        userRole.setRole(role);
        userRoleRepo.save(userRole);

        log.info("Successfully created user role for employee {}", nhanVien.getMaNhanVien());
    }

    @Override
    public List<EmployeeResponse> getManagerOfBranch(String maChiNhanh) {
        log.info("Getting managers for branch: {}", maChiNhanh);

        if (!chiNhanhRepo.existsById(maChiNhanh)) {
            throw new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy chi nhánh " + maChiNhanh);
        }

        return nhanVienRepo.findManagersByChiNhanh(maChiNhanh);
    }

    @Override
    public void createEmployee(CreateEmployeeRequest request) {
        log.info("Creating new employee for branch: {}", request.getMaChiNhanh());
        String userID = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        NhanVien quanLy = nhanVienRepo.findById(userID)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy quản lý!"));

        if (nhanVienRepo.existsByTaiKhoan(request.getTaiKhoan())) {
            throw new AppException(ErrorCode.ACCOUNT_EXISTED, "Tên tài khoản này đã tồn tại!");
        }

        NhanVien nhanVien = new NhanVien();
        nhanVien.setTenNhanVien(request.getTenNhanVien().trim().replaceAll("\\s+", " "));
        nhanVien.setTaiKhoan(request.getTaiKhoan());
        nhanVien.setMatKhau(request.getMatKhau());

        LocalDate ngayLam = (request.getNgayLamViec() == null) ? LocalDate.now() : request.getNgayLamViec();
        nhanVien.setNgayLamViec(ngayLam);

        nhanVien.setChiNhanh(quanLy.getChiNhanh());
        nhanVien.setTrangThai(1);
        nhanVienRepo.save(nhanVien);
        log.info("Successfully created new employee: {}", nhanVien.getMaNhanVien());

        Role role = roleRepo.findById(request.getRoleId())
                .orElseThrow(() -> {
                    log.info("Missing role: {}", request.getRoleId());
                    return new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy vai trò!");
                });

        UserRole userRole = new UserRole();
        userRole.setNhanVien(nhanVien);
        userRole.setRole(role);
        userRoleRepo.save(userRole);

        log.info("Successfully created new user role for employee: {}", nhanVien.getMaNhanVien());
    }

    @Override
    public List<EmployeeResponse> getEmployees() {
        String userID = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        log.info("Getting employees for manager: {}", userID);

        return nhanVienRepo.findEmployeesByManager(userID);
    }

    @Override
    @Transactional
    public boolean updateEmployee(String maNhanVien, CreateEmployeeRequest request) {
        NhanVien nhanVien = nhanVienRepo.findById(maNhanVien)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy nhân viên!"));

        if (nhanVienRepo.existsByTaiKhoan(request.getTaiKhoan()) && !Objects.equals(nhanVien.getTaiKhoan(), request.getTaiKhoan())) {
            throw new AppException(ErrorCode.ACCOUNT_EXISTED, "Tài khoản này đã tồn tại!");
        }

        log.info("Updating employee: {}", maNhanVien);
        boolean changed = false;
        changed |= updateIfChange(nhanVien::getTenNhanVien, request.getTenNhanVien(), nhanVien::setTenNhanVien);
        changed |= updateIfChange(nhanVien::getTaiKhoan, request.getTaiKhoan(), nhanVien::setTaiKhoan);
        changed |= updateIfChange(nhanVien::getMatKhau, request.getMatKhau(), nhanVien::setMatKhau);
        LocalDate ngayLam = (request.getNgayLamViec() == null) ? LocalDate.now() : request.getNgayLamViec();
        changed |= updateIfChange(nhanVien::getNgayLamViec, ngayLam, request::setNgayLamViec);
        if (userRoleRepo.updateRole(maNhanVien, request.getRoleId()) != 0) {
            changed = true;
        }
        if (!changed) {
            log.info("Nothing has changed, employee: {}", maNhanVien);
        } else {
            log.info("Successfully updated employee: {}", maNhanVien);
        }
        return changed;
    }

    @Override
    @Transactional
    public void activateEmployee(String maNhanVien) {
        NhanVien nhanVien = nhanVienRepo.findById(maNhanVien)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy quản lý!"));

        log.info("Activating employee: {}", maNhanVien);

        String userID = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();

        UserRole userRole = userRoleRepo.findByNhanVien_MaNhanVien(userID)
                .orElseThrow(() -> {
                    log.info("User can access /api/emp/activate but missing user role");
                    return new AppException(ErrorCode.UNAUTHORIZED, "Người dùng bị thiếu quyền!");
                });

        NhanVien currentUser = userRole.getNhanVien();

        if (userRole.getRole().getRoleName().equalsIgnoreCase("MANAGER")) {
            if (!nhanVien.getChiNhanh().getMaChiNhanh()
                    .equals(currentUser.getChiNhanh().getMaChiNhanh())) {
                throw new AppException(ErrorCode.UNAUTHORIZED,
                        "Không phải quản lý chi nhánh này!");
            }
        }

        if (nhanVien.getTrangThai() == 0) {
            nhanVien.setTrangThai(1);
            log.info("Activated employee: {}", maNhanVien);
        } else {
            log.info("Employee: {} has already been activated", maNhanVien);
            throw new AppException(ErrorCode.INVALID_STATE, "Nhân viên vẫn đang làm việc!");
        }
    }

    @Override
    @Transactional
    public void deactivateEmployee(String maNhanVien) {
        NhanVien nhanVien = nhanVienRepo.findById(maNhanVien)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy quản lý!"));

        log.info("Deactivating employee: {}", maNhanVien);

        String userID = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();

        UserRole userRole = userRoleRepo.findByNhanVien_MaNhanVien(userID)
                .orElseThrow(() -> {
                    log.info("User can access /api/emp/deactivate but missing user role");
                    return new AppException(ErrorCode.UNAUTHORIZED, "Người dùng bị thiếu quyền!");
                });

        NhanVien currentUser = userRole.getNhanVien();

        if (userRole.getRole().getRoleName().equalsIgnoreCase("MANAGER")) {
            if (!nhanVien.getChiNhanh().getMaChiNhanh()
                    .equals(currentUser.getChiNhanh().getMaChiNhanh())) {
                throw new AppException(ErrorCode.UNAUTHORIZED,
                        "Không phải quản lý chi nhánh này!");
            }
        }

        if (nhanVien.getTrangThai() == 1) {
            nhanVien.setTrangThai(0);
            log.info("Deactivated employee: {}", maNhanVien);
        } else {
            log.info("Employee: {} has already been deactivated", maNhanVien);
            throw new AppException(ErrorCode.INVALID_STATE, "Nhân viên đã nghỉ việc rồi!");
        }
    }
}
