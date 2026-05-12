package com.example.phungloc.impl;

import com.example.phungloc.dto.request.AddProductRequest;
import com.example.phungloc.dto.request.DeleteProductRequest;
import com.example.phungloc.dto.response.menu_response.MenuResponse;
import com.example.phungloc.dto.response.menu_response.SizeResponse;
import com.example.phungloc.entities.*;
import com.example.phungloc.repositories.*;
import com.example.phungloc.services.MenuService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepo menuRepo;
    @Autowired
    private ChiTietMenuRepo chiTietMenuRepo;
    @Autowired
    private SanPhamRepo sanPhamRepo;
    @Autowired
    private NhanVienRepo nhanVienRepo;
    @Autowired
    private KichCoRepo kichCoRepo;
    @Autowired
    private CongThucRepo congThucRepo;

    @Override
    @Transactional
    public ResponseEntity<String> addProductMenu(AddProductRequest request) {
        // get maNhanVien
        String maNhanVien = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        NhanVien nhanVien = nhanVienRepo.findById(maNhanVien)
                .orElseThrow(() -> new RuntimeException("Employee doesn't exist!"));
        // get menu
        Menu menu = menuRepo.findByChiNhanh_MaChiNhanh(nhanVien.getChiNhanh().getMaChiNhanh())
                .orElseThrow(() -> new RuntimeException("Menu doesn't exist"));
        // get sanPham
        SanPham sanPham = sanPhamRepo.findById(request.getMaSanPham())
                .orElseThrow(() -> new RuntimeException("Product doesn't exist"));
        if (chiTietMenuRepo.existsByMenu_MaMenuAndSanPham_MaSanPham(menu.getMaMenu(), request.getMaSanPham())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Product existed!");
        }

        // create new ChiTietMenu
        ChiTietMenu chiTietMenu = new ChiTietMenu();
        chiTietMenu.setMenu(menu);
        chiTietMenu.setSanPham(sanPham);
        chiTietMenuRepo.save(chiTietMenu);

        return ResponseEntity.ok().body("Successfully added product to menu!");
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteProductMenu(DeleteProductRequest request) {
        // get maNhanVien
        String maNhanVien = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        NhanVien nhanVien = nhanVienRepo.findById(maNhanVien)
                .orElseThrow(() -> new RuntimeException("Employee doesn't exist!"));
        // get menu
        Menu menu = menuRepo.findByChiNhanh_MaChiNhanh(nhanVien.getChiNhanh().getMaChiNhanh())
                .orElseThrow(() -> new RuntimeException("Menu doesn't exist"));
        // find chiTietMenu
        ChiTietMenu chiTietMenu = chiTietMenuRepo.findById(request.getMaCtMenu())
                .orElseThrow(() -> new RuntimeException("ChiTietMenu doesn't exist"));
        if (!chiTietMenu.getMenu().getMaMenu().equals(menu.getMaMenu())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        // delete
        chiTietMenuRepo.delete(chiTietMenu);

        return ResponseEntity.ok().body("Successfully deleted product from menu!");
    }

    @Override
    public List<MenuResponse> viewMenu() {
        // get maNhanVien
        String maNhanVien = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        NhanVien nhanVien = nhanVienRepo.findById(maNhanVien)
                .orElseThrow(() -> new RuntimeException("Employee doesn't exist!"));
        // get menu
        Menu menu = menuRepo.findByChiNhanh_MaChiNhanh(nhanVien.getChiNhanh().getMaChiNhanh())
                .orElseThrow(() -> new RuntimeException("Menu doesn't exist"));
        // get ChiTietMenu
        List<ChiTietMenu> danhSachSanPham = chiTietMenuRepo.findByMenu(menu);

        List<MenuResponse> danhSach = new ArrayList<>();
        for (ChiTietMenu chiTiet : danhSachSanPham) {
            List<SizeResponse> sizes = congThucRepo.findKichCoBySanPham(chiTiet.getSanPham().getMaSanPham());

            danhSach.add(new MenuResponse(
                    chiTiet.getMaCtMenu(),
                    chiTiet.getSanPham().getMaSanPham(),
                    chiTiet.getSanPham().getTenSanPham(),
                    chiTiet.getSanPham().getMoTa(),
                    chiTiet.getSanPham().getGiaBan(),
                    chiTiet.getSanPham().getHinhAnh(),
                    chiTiet.getSanPham().getLoaiSanPham().getMaLoai(),
                    chiTiet.getSanPham().getLoaiSanPham().getTenLoai(),
                    chiTiet.getSanPham().getTrangThai(),
                    sizes
            ));
        }
        return danhSach;
    }
}
