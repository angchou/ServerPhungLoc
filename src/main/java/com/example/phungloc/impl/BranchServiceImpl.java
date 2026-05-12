package com.example.phungloc.impl;

import com.example.phungloc.dto.request.branch_request.CreateBranchRequest;
import com.example.phungloc.dto.response.branch_response.BranchResponse;
import com.example.phungloc.entities.*;
import com.example.phungloc.repositories.*;
import com.example.phungloc.services.BranchService;
import com.example.phungloc.exception.AppException;
import com.example.phungloc.exception.ErrorCode;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.phungloc.util.UpdateHelper.updateIfChange;

@Service
public class BranchServiceImpl implements BranchService {

    private static final Logger log = LoggerFactory.getLogger(BranchServiceImpl.class);
    @Autowired
    private ChiNhanhRepo chiNhanhRepo;
    @Autowired
    private UserRoleRepo userRoleRepo;
    @Autowired
    private KhoRepo khoRepo;
    @Autowired
    private NguyenLieuRepo nguyenLieuRepo;
    @Autowired
    private ChiTietKhoRepo chiTietKhoRepo;
    @Autowired
    private MenuRepo menuRepo;

    @Override
    @Transactional
    public void createBranch(CreateBranchRequest request) {
        log.info("Creating a new Branch: {}", request.getTenChiNhanh());
        ChiNhanh chiNhanh = new ChiNhanh();
        chiNhanh.setTenChiNhanh(request.getTenChiNhanh().trim().replaceAll("\\s+", " "));
        chiNhanh.setDiaChi(request.getDiaChi().trim().replaceAll("\\s+", " "));

        LocalDate ngayBD = (request.getNgayHoatDong() == null) ? LocalDate.now() : request.getNgayHoatDong();
        chiNhanh.setNgayHoatDong(ngayBD);
        chiNhanh.setTrangThai(0);

        chiNhanhRepo.save(chiNhanh);
        log.info("Created a new Branch: {}", chiNhanh.getMaChiNhanh());

        // CREATE NEW WAREHOUSE
        Kho kho = new Kho();
        kho.setChiNhanh(chiNhanh);
        khoRepo.save(kho);

        // CREATE NEW MENU
        Menu menu = new Menu();
        menu.setChiNhanh(chiNhanh);
        menuRepo.save(menu);

        // DUPLICATE INGREDIENTS
        List<NguyenLieu> tatCaNguyenLieu = nguyenLieuRepo.findAll();
        List<ChiTietKho> danhSachChiTietKho = tatCaNguyenLieu.stream().map(nl -> {
            ChiTietKho ctk = new ChiTietKho();
            ctk.setKho(kho);
            ctk.setNguyenLieu(nl);
            ctk.setSoLuong(BigDecimal.ZERO);
            return ctk;
        }).collect(Collectors.toList());

        chiTietKhoRepo.saveAll(danhSachChiTietKho);

        log.info("Done creating new Warehouse, new Menu for: {}", chiNhanh.getMaChiNhanh());
    }

    @Override
    @Transactional
    public boolean updateBranch(String maChiNhanh, CreateBranchRequest request) {
        ChiNhanh chiNhanh = chiNhanhRepo.findById(maChiNhanh)
                .orElseThrow(() -> {
                    log.info("Branch not found: {}", maChiNhanh);
                    return new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy chi nhánh!");
                });

        log.info("Updating branch: {}", maChiNhanh);
        boolean changed = false;
        changed |= updateIfChange(chiNhanh::getTenChiNhanh, request.getTenChiNhanh().trim().replaceAll("\\s+", " "), chiNhanh::setTenChiNhanh);
        changed |= updateIfChange(chiNhanh::getDiaChi, request.getDiaChi().trim().replaceAll("\\s+", " "), chiNhanh::setDiaChi);
        LocalDate ngayBD = (request.getNgayHoatDong() == null) ? LocalDate.now() : request.getNgayHoatDong();
        changed |= updateIfChange(chiNhanh::getNgayHoatDong, ngayBD, chiNhanh::setNgayHoatDong);

        if (!changed) {
            log.info("Nothing has changed - branch: {}", maChiNhanh);
        } else {
            log.info("Successfully updated branch: {}", maChiNhanh);
        }

        return changed;
    }

    @Override
    @Transactional
    public void openBranch(String maChiNhanh) {
        ChiNhanh chiNhanh = chiNhanhRepo.findById(maChiNhanh)
                .orElseThrow(() -> {
                    log.info("Opening branch not found: {}", maChiNhanh);
                    return new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy chi nhánh!");
                });

        log.info("Opening branch: {}", maChiNhanh);
        boolean changed = updateIfChange(chiNhanh::getTrangThai, 1, chiNhanh::setTrangThai);
        if (changed) {
            log.info("Successfully opened branch: {}", maChiNhanh);
        } else {
            log.info("Branch: {} is already opened", maChiNhanh);
            throw new AppException(ErrorCode.NOTHING_CHANGE, "Chi nhánh đã được mở cửa rồi!");
        }
    }

    @Override
    @Transactional
    public void closeBranch(String maChiNhanh) {
        ChiNhanh chiNhanh = chiNhanhRepo.findById(maChiNhanh)
                .orElseThrow(() -> {
                    log.info("Closing branch not found: {}", maChiNhanh);
                    return new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy chi nhánh!");
                });

        log.info("Closing branch: {}", maChiNhanh);
        boolean changed = updateIfChange(chiNhanh::getTrangThai, 0, chiNhanh::setTrangThai);
        if (changed) {
            log.info("Successfully closed branch: {}", maChiNhanh);
        } else {
            log.info("Branch: {} is already closed", maChiNhanh);
            throw new AppException(ErrorCode.NOTHING_CHANGE, "Chi nhánh đã bị đóng cửa rồi!");
        }
    }

    @Override
    public List<BranchResponse> getBranches() {
        String maNhanVien = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();

        log.info("Getting branches for region manager: {}", maNhanVien);
        List<ChiNhanh> danhSachChiNhanh = chiNhanhRepo.findByTrangThaiInOrderByMaChiNhanhAsc(List.of(0, 1));

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
