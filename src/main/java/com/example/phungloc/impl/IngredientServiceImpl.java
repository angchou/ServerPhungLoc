package com.example.phungloc.impl;

import com.example.phungloc.dto.request.ingredient_request.CreateIngredientRequest;
import com.example.phungloc.dto.response.ingredient_response.IngredientResponse;
import com.example.phungloc.entities.*;
import com.example.phungloc.repositories.*;
import com.example.phungloc.services.IngredientService;
import com.example.phungloc.exception.AppException;
import com.example.phungloc.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.example.phungloc.util.UpdateHelper.updateIfChange;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private NguyenLieuRepo nguyenLieuRepo;
    @Autowired
    private UserRoleRepo userRoleRepo;
    @Autowired
    private NhaCungCapRepo nhaCungCapRepo;
    @Autowired
    private KhoRepo khoRepo;
    @Autowired
    private ChiTietKhoRepo chiTietKhoRepo;

    @Override
    @Transactional
    public void createIngredient(CreateIngredientRequest request) {
        log.info("Creating new ingredient: {}", request.getTenNguyenLieu());
        NguyenLieu nguyenLieu = new NguyenLieu();
        NhaCungCap nhaCungCap = nhaCungCapRepo.findById(request.getMaNcc())
                        .orElseThrow(() -> {
                            log.info("Supplier: {} not found", request.getMaNcc());
                            return new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy nhà cung cấp!");
                        });
        nguyenLieu.setTenNguyenLieu(request.getTenNguyenLieu().trim().replaceAll("\\s+", " "));
        nguyenLieu.setDonVi(request.getDonVi().trim().replaceAll("\\s+", " "));
        nguyenLieu.setGiaBan(request.getGiaBan());
        nguyenLieu.setNhaCungCap(nhaCungCap);
        nguyenLieu.setTrangThai(0);
        nguyenLieuRepo.save(nguyenLieu);
        log.info("Successfully created new ingredient: {}", nguyenLieu.getMaNguyenLieu());

        List<Kho> danhSachKho = khoRepo.findByChiNhanh_TrangThai(1);
        for (Kho kho : danhSachKho) {
            ChiTietKho chiTietKho = new ChiTietKho();
            chiTietKho.setKho(kho);
            chiTietKho.setNguyenLieu(nguyenLieu);
            chiTietKho.setSoLuong(BigDecimal.valueOf(0));
            chiTietKhoRepo.save(chiTietKho);
        }
        log.info("Successfully duplicated ingredient: {} for all active warehouse", nguyenLieu.getMaNguyenLieu());
    }

    @Override
    @Transactional
    public boolean updateIngredient(String maNguyenLieu, CreateIngredientRequest request) {
        log.info("Updating ingredient: {}", maNguyenLieu);
        NguyenLieu nguyenLieu = nguyenLieuRepo.findById(maNguyenLieu)
                .orElseThrow(() -> {
                    log.info("Ingredient {} not found", maNguyenLieu);
                    return new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy nguyên liệu!");
                });
        boolean changed = false;
        changed |= updateIfChange(nguyenLieu::getTenNguyenLieu, request.getTenNguyenLieu().trim().replaceAll("\\s+", " "), nguyenLieu::setTenNguyenLieu);
        changed |= updateIfChange(nguyenLieu::getDonVi, request.getDonVi().trim().replaceAll("\\s+", " "), nguyenLieu::setDonVi);
        changed |= updateIfChange(nguyenLieu::getGiaBan, request.getGiaBan(), nguyenLieu::setGiaBan);
        if (!Objects.equals(nguyenLieu.getNhaCungCap().getMaNcc(), request.getMaNcc())) {
            NhaCungCap nhaCungCap = nhaCungCapRepo.findById(request.getMaNcc())
                    .orElseThrow(() -> {
                        log.info("New supplier: {} not found", request.getMaNcc());
                        return new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy nhà cung cấp mới!");
                    });
            nguyenLieu.setNhaCungCap(nhaCungCap);
            changed = true;
        }
        if (changed) {
            log.info("Successfully updated ingredient: {}", maNguyenLieu);
        } else {
            log.info("Nothing has changed, ingredient: {}", maNguyenLieu);
        }
        return changed;
    }

    @Override
    @Transactional
    public void disableIngredient(String maNguyenLieu) {
        log.info("Disabling ingredient: {}", maNguyenLieu);
        NguyenLieu nguyenLieu = nguyenLieuRepo.findById(maNguyenLieu)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy nguyên liệu!"));
        if (nguyenLieu.getTrangThai() == 1) {
            nguyenLieu.setTrangThai(0);
            log.info("Successfully disabled ingredient: {}", maNguyenLieu);
        } else {
            log.info("Ingredient: {} has already been disabled", maNguyenLieu);
            throw new AppException(ErrorCode.NOTHING_CHANGE, "Nguyên liệu đã bị ngừng sử dụng rồi!");
        }
    }

    @Override
    @Transactional
    public void enableIngredient(String maNguyenLieu) {
        log.info("Enabling ingredient: {}", maNguyenLieu);
        NguyenLieu nguyenLieu = nguyenLieuRepo.findById(maNguyenLieu)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy nguyên liệu!"));
        if (nguyenLieu.getTrangThai() == 0) {
            nguyenLieu.setTrangThai(1);
            log.info("Successfully enabled ingredient: {}", maNguyenLieu);
        } else {
            log.info("Ingredient: {} has already been enabled", maNguyenLieu);
            throw new AppException(ErrorCode.NOTHING_CHANGE, "Nguyên liệu đang được sử dụng rồi!");
        }
    }

    @Override
    public List<IngredientResponse> getIngredients() {
        log.info("Getting all ingredients");
        List<NguyenLieu> danhSachNguyenLieu = nguyenLieuRepo.findByTrangThaiInOrderByMaNguyenLieu(List.of(0, 1));

        return danhSachNguyenLieu.stream().map(
                nguyenLieu -> new IngredientResponse(
                        nguyenLieu.getMaNguyenLieu(),
                        nguyenLieu.getTenNguyenLieu(),
                        nguyenLieu.getDonVi(),
                        nguyenLieu.getGiaBan(),
                        nguyenLieu.getTrangThai(),
                        nguyenLieu.getNhaCungCap().getMaNcc(),
                        nguyenLieu.getNhaCungCap().getMaNcc()
                )
        ).toList();
    }

    @Override
    public List<IngredientResponse> getIngredientsOfSupplier(String maNcc) {
        log.info("Getting all ingredients are supplied by supplier: {}", maNcc);
        List<NguyenLieu> danhSachNguyenLieu = nguyenLieuRepo.findAllOfSupplier(List.of(0, 1), maNcc);
        return danhSachNguyenLieu.stream().map(
                nl -> new IngredientResponse(
                        nl.getMaNguyenLieu(),
                        nl.getTenNguyenLieu(),
                        nl.getDonVi(),
                        nl.getGiaBan(),
                        nl.getTrangThai(),
                        maNcc,
                        nl.getNhaCungCap().getTenNcc()
                )
        ).toList();
    }

    @Override
    public List<IngredientResponse> getEnabledIngredients() {
        log.info("Getting all enabled ingredients");
        List<NguyenLieu> danhSachNguyenLieu = nguyenLieuRepo.findByTrangThaiOrderByMaNguyenLieu(1);

        return danhSachNguyenLieu.stream().map(
                nl -> new IngredientResponse(
                        nl.getMaNguyenLieu(),
                        nl.getTenNguyenLieu(),
                        nl.getDonVi(),
                        nl.getGiaBan(),
                        nl.getTrangThai(),
                        nl.getNhaCungCap().getMaNcc(),
                        nl.getNhaCungCap().getTenNcc()
                )
        ).toList();
    }

    @Override
    public List<IngredientResponse> getDisabledIngredients() {
        log.info("Getting all disabled ingredients");
        List<NguyenLieu> danhSachNguyenLieu = nguyenLieuRepo.findByTrangThaiOrderByMaNguyenLieu(0);
        return danhSachNguyenLieu.stream().map(
                nl -> new IngredientResponse(
                        nl.getMaNguyenLieu(),
                        nl.getTenNguyenLieu(),
                        nl.getDonVi(),
                        nl.getGiaBan(),
                        nl.getTrangThai(),
                        nl.getNhaCungCap().getMaNcc(),
                        nl.getNhaCungCap().getTenNcc()
                )
        ).toList();
    }
}
