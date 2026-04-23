package com.example.phungloc.impl;

import com.example.phungloc.dto.request.CreateProductRequest;
import com.example.phungloc.dto.response.ProductResponse;
import com.example.phungloc.entities.LoaiSanPham;
import com.example.phungloc.entities.Menu;
import com.example.phungloc.entities.SanPham;
import com.example.phungloc.entities.UserRole;
import com.example.phungloc.repositories.LoaiSanPhamRepo;
import com.example.phungloc.repositories.MenuRepo;
import com.example.phungloc.repositories.SanPhamRepo;
import com.example.phungloc.repositories.UserRoleRepo;
import com.example.phungloc.services.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    @Transactional
    public String createProduct(CreateProductRequest request) {
        // create new product
        SanPham sanPham = new SanPham();
        sanPham.setTenSanPham(request.getTenSanPham());
        sanPham.setMoTa(request.getMoTa());
        sanPham.setGiaBan(request.getGiaBan());
        sanPham.setTrangThai(1);

        // get LoaiSanPham
        Optional<LoaiSanPham> loaiSanPham = loaiSanPhamRepo.findById(request.getMaLoai());
        sanPham.setLoaiSanPham(loaiSanPham.get());

        // after sending maSanPham -> frontend has to fetch /api/image/upload/{maSanPham} to upload product's image
        // if uploading fail, employee still be able to update image
        return sanPhamRepo.save(sanPham).getMaSanPham();
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateProduct(String maSanPham, CreateProductRequest request) {
        // no Menu update
        // no hinhAnh update: image can be updated separately by fetching "/api/image/upload/{maSanPham}"
        // find product
        SanPham sanPham = sanPhamRepo.findById(maSanPham)
                .orElseThrow(() -> new RuntimeException("Product doesn't exist!"));

        // check tenSanPham
        if (!sanPham.getTenSanPham().equals(request.getTenSanPham())) {
            sanPham.setTenSanPham(request.getTenSanPham());
        }
        // check moTa
        if (!sanPham.getMoTa().equals(request.getMoTa())) {
            sanPham.setMoTa(request.getMoTa());
        }
        // check loai
        if (!sanPham.getLoaiSanPham().getMaLoai().equals(request.getMaLoai())) {
            // find LoaiSanPham
            LoaiSanPham loaiSanPham = loaiSanPhamRepo.findById(request.getMaLoai()).get();
            sanPham.setLoaiSanPham(loaiSanPham);
        }

        return ResponseEntity.ok().body("Successfully updated product!");
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteProduct(String maSanPham) {
        // find SanPham
        SanPham sanPham = sanPhamRepo.findById(maSanPham)
                .orElseThrow(() -> new RuntimeException("Product doesn't exist!"));
        // check trangThai
        if (sanPham.getTrangThai() == 2) {
            throw new RuntimeException("Product has stopped selling!");
        }
        // delete (set trangThai to stopped selling)
        sanPham.setTrangThai(2);
        return ResponseEntity.ok().body("Successfully deleted product!");
    }

    @Override
    public List<ProductResponse> getProduct() {
        // Region manager can see products which have been stopped selling
        // ---
        // Manager can only see products which are still active
        // get maNhanvien
        String maNhanVien = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        // find user_role
        UserRole userRole = userRoleRepo.findByNhanVien_MaNhanVien(maNhanVien);

        List<SanPham> danhSachSanPham = List.of();

        if (userRole.getRole().getRoleName().toUpperCase().equals("MANAGER")) {
            danhSachSanPham = sanPhamRepo.findByTrangThai(1);
        }
        else if (userRole.getRole().getRoleName().toUpperCase().equals("REGION_MANAGER")) {
            danhSachSanPham = sanPhamRepo.findAll();
        }

        return danhSachSanPham.stream().map(
                sanPham -> new ProductResponse(
                        sanPham.getMaSanPham(),
                        sanPham.getTenSanPham(),
                        sanPham.getMoTa(),
                        sanPham.getGiaBan(),
                        sanPham.getLoaiSanPham().getMaLoai(),
                        sanPham.getTrangThai()
                )
        ).toList();
    }
}
