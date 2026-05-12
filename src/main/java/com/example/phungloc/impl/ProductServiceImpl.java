package com.example.phungloc.impl;

import com.example.phungloc.dto.request.product_request.CreateProductRequest;
import com.example.phungloc.dto.request.product_request.CreateProductTypeRequest;
import com.example.phungloc.dto.response.product_response.ProductResponse;
import com.example.phungloc.dto.response.product_response.ProductTypeResponse;
import com.example.phungloc.entities.LoaiSanPham;
import com.example.phungloc.entities.SanPham;
import com.example.phungloc.exception.AppException;
import com.example.phungloc.exception.ErrorCode;
import com.example.phungloc.repositories.*;
import com.example.phungloc.services.ProductService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static com.example.phungloc.util.UpdateHelper.updateIfChange;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private SanPhamRepo sanPhamRepo;
    @Autowired
    private MenuRepo menuRepo;
    @Autowired
    private LoaiSanPhamRepo loaiSanPhamRepo;
    @Autowired
    private UserRoleRepo userRoleRepo;

    @Override
    public List<ProductTypeResponse> getProductTypes() {
        log.info("Getting all product types");
        List<LoaiSanPham> danhSachLoaiSP = loaiSanPhamRepo.findAllByOrderByMaLoai();
        return danhSachLoaiSP.stream().map(
                lsp -> new ProductTypeResponse(
                        lsp.getMaLoai(),
                        lsp.getTenLoai(),
                        sanPhamRepo.countByLoaiSanPham_MaLoai(lsp.getMaLoai())
                )
        ).toList();
    }

    @Override
    @Transactional
    public void createProductType(CreateProductTypeRequest request) {
        log.info("Creating new product type: {}", request.getTenLoai());
        LoaiSanPham loaiSanPham = new LoaiSanPham();
        loaiSanPham.setTenLoai(request.getTenLoai().trim().replaceAll("\\s+", " "));
        loaiSanPhamRepo.save(loaiSanPham);
        log.info("Created new product type: {}", loaiSanPham.getMaLoai());
    }

    @Override
    @Transactional
    public boolean updateProductType(String maLoai, CreateProductTypeRequest request) {
        LoaiSanPham loaiSanPham = loaiSanPhamRepo.findById(maLoai)
                .orElseThrow(() -> {
                    log.info("Product type: {} not found while updating", maLoai);
                    return new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy loại sản phẩm!");
                });
        log.info("Updating product type: {}", maLoai);
        boolean changed = false;
        changed |= updateIfChange(loaiSanPham::getTenLoai, request.getTenLoai().trim().replaceAll("\\s+", " "), loaiSanPham::setTenLoai);
        if (changed) {
            log.info("Successfully updated product type: {}", maLoai);
        } else {
            log.info("Nothing has changed, product type: {}", maLoai);
        }
        return changed;
    }

    @Override
    @Transactional
    public void deleteProductType(String maLoai) {
        LoaiSanPham loaiSanPham = loaiSanPhamRepo.findById(maLoai)
                .orElseThrow(() -> {
                    log.info("Product type: {} not found while deleting", maLoai);
                    return new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy loại sản phẩm!");
                });
        log.info("Deleting product type: {}", maLoai);
        if (sanPhamRepo.existsByLoaiSanPham_MaLoai(maLoai)) {
            log.info("Can not delete product type: {} because there are product(s) belong to this product type", maLoai);
            throw new AppException(ErrorCode.INVALID_DELETE, "Không thể xóa loại sản phẩm vì vẫn còn sản phẩm thuộc loại sản phẩm này!");
        }
        loaiSanPhamRepo.deleteById(maLoai);
        log.info("Successfully deleted product type: {}", maLoai);
    }

    @Override
    @Transactional
    public void createProduct(CreateProductRequest request) {
        log.info("Creating new product: {}", request.getTenSanPham());
        SanPham sanPham = new SanPham();
        LoaiSanPham loaiSanPham = loaiSanPhamRepo.findById(request.getMaLoai())
                        .orElseThrow(() -> {
                            log.info("Product type: {} not found", request.getMaLoai());
                            return new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy loại sản phẩm!");
                        });
        sanPham.setTenSanPham(request.getTenSanPham().trim().replaceAll("\\s+", " "));
        sanPham.setMoTa(request.getMoTa() != null ? request.getMoTa().trim().replaceAll("\\s+", " ") : "");
        sanPham.setGiaBan(request.getGiaBan());
        sanPham.setTrangThai(0);
        sanPham.setLoaiSanPham(loaiSanPham);
        sanPhamRepo.save(sanPham);
        log.info("Successfully created new product: {}", sanPham.getMaSanPham());
    }

    @Override
    @Transactional
    public boolean updateProduct(String maSanPham, CreateProductRequest request) {
        SanPham sanPham = sanPhamRepo.findById(maSanPham)
                .orElseThrow(() -> {
                    log.info("Product: {} not found", maSanPham);
                    return new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy sản phẩm!");
                });
        log.info("Updating product: {}", maSanPham);
        boolean changed = false;
        changed |= updateIfChange(sanPham::getTenSanPham, request.getTenSanPham().trim().replaceAll("\\s+", " "), sanPham::setTenSanPham);
        changed |= updateIfChange(sanPham::getMoTa, request.getMoTa() != null ? request.getMoTa().trim().replaceAll("\\s+", " ") : null, sanPham::setMoTa);
        changed |= updateIfChange(sanPham::getGiaBan, request.getGiaBan(), sanPham::setGiaBan);
        if (!Objects.equals(sanPham.getLoaiSanPham().getMaLoai(), request.getMaLoai())) {
            LoaiSanPham loaiSanPham = loaiSanPhamRepo.findById(request.getMaLoai())
                    .orElseThrow(() -> {
                        log.info("Product: {} not found while updating", request.getMaLoai());
                        return new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy loại sản phẩm!");
                    });
            sanPham.setLoaiSanPham(loaiSanPham);
            changed = true;
        }
        if (changed) {
            log.info("Successfully updated product: {}", maSanPham);
        } else {
            log.info("Nothing has changed, product: {}", maSanPham);
        }
        return changed;
    }

    @Override
    @Transactional
    public void disableProduct(String maSanPham) {
        SanPham sanPham = sanPhamRepo.findById(maSanPham)
                .orElseThrow(() -> {
                    log.info("Product: {} not found while disabling", maSanPham);
                    return new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy sản phẩm!");
                });
        if (sanPham.getTrangThai() == 1) {
            sanPham.setTrangThai(0);
            log.info("Successfully disabled product: {}", maSanPham);
        } else {
            log.info("Product: {} has already been disabled!", maSanPham);
            throw new AppException(ErrorCode.INVALID_STATE, "Sản phẫm đã bị ngừng bán rồi!");
        }
    }

    @Override
    @Transactional
    public void enableProduct(String maSanPham) {
        SanPham sanPham = sanPhamRepo.findById(maSanPham)
                .orElseThrow(() -> {
                    log.info("Product: {} not found while enabling", maSanPham);
                    return new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy sản phẩm!");
                });
        if (sanPham.getTrangThai() == 0) {
            sanPham.setTrangThai(1);
            log.info("Successfully enabled product: {}", maSanPham);
        } else {
            log.info("Product: {} has already been enabled!", maSanPham);
            throw new AppException(ErrorCode.INVALID_STATE, "Sản phẫm đã được mở bán rồi!");
        }
    }

    @Override
    public List<ProductResponse> getEnabledProducts() {
        log.info("Getting enabled products");
        List<SanPham> danhSachSanPham = sanPhamRepo.findAllWithLoaiSanPhamByTrangThai(1);

        return danhSachSanPham.stream().map(
                sanPham -> new ProductResponse(
                        sanPham.getMaSanPham(),
                        sanPham.getTenSanPham(),
                        sanPham.getMoTa(),
                        sanPham.getGiaBan(),
                        sanPham.getLoaiSanPham().getMaLoai(),
                        sanPham.getTrangThai(),
                        sanPham.getHinhAnh(),
                        sanPham.getLoaiSanPham().getTenLoai()
                )
        ).toList();
    }

    @Override
    public List<ProductResponse> getDisabledProducts() {
        log.info("Getting disabled products");
        String maNhanVien = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        List<SanPham> danhSachSanPham = sanPhamRepo.findAllWithLoaiSanPhamByTrangThai(0);

        return danhSachSanPham.stream().map(
                sanPham -> new ProductResponse(
                        sanPham.getMaSanPham(),
                        sanPham.getTenSanPham(),
                        sanPham.getMoTa(),
                        sanPham.getGiaBan(),
                        sanPham.getLoaiSanPham().getMaLoai(),
                        sanPham.getTrangThai(),
                        sanPham.getHinhAnh(),
                        sanPham.getLoaiSanPham().getTenLoai()
                )
        ).toList();
    }
}
