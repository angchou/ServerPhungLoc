package com.example.phungloc.impl;

import com.example.phungloc.dto.request.AddProductRequest;
import com.example.phungloc.dto.request.DeleteProductRequest;
import com.example.phungloc.dto.request.MenuResponse;
import com.example.phungloc.dto.response.ProductResponse;
import com.example.phungloc.entities.ChiTietMenu;
import com.example.phungloc.entities.Menu;
import com.example.phungloc.entities.NhanVien;
import com.example.phungloc.entities.SanPham;
import com.example.phungloc.repositories.ChiTietMenuRepo;
import com.example.phungloc.repositories.MenuRepo;
import com.example.phungloc.repositories.NhanVienRepo;
import com.example.phungloc.repositories.SanPhamRepo;
import com.example.phungloc.services.MenuService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    @Override
    @Transactional
    public ResponseEntity<String> addProductMenu(AddProductRequest request) {
        // get maNhanVien
        String maNhanVien = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        NhanVien nhanVien = nhanVienRepo.findById(maNhanVien)
                .orElseThrow(() -> new RuntimeException("Employee doesn't exist!"));

        // get menu
        Menu menu = menuRepo.findById(request.getMaMenu())
                .orElseThrow(() -> new RuntimeException("Menu doesn't exist"));
        // get sanPham
        SanPham sanPham = sanPhamRepo.findById(request.getMaSanPham())
                .orElseThrow(() -> new RuntimeException("Product doesn't exist"));

        // check if employee is the manager of the branch's menu
        if (!nhanVien.getChiNhanh().getMaChiNhanh().equals(menu.getChiNhanh().getMaChiNhanh())) {
            throw new RuntimeException("You don't have enough permission!");
        }

        // create new ChiTietMenu
        ChiTietMenu chiTietMenu = new ChiTietMenu();
        chiTietMenu.setMenu(menu);
        chiTietMenu.setSanPham(sanPham);

        // save chiTietMenu
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
        Menu menu = menuRepo.findById(request.getMaMenu())
                .orElseThrow(() -> new RuntimeException("Menu doesn't exist"));
        // check if employee is the manager of the branch's menu
        if (!nhanVien.getChiNhanh().getMaChiNhanh().equals(menu.getChiNhanh().getMaChiNhanh())) {
            throw new RuntimeException("You don't have enough permission!");
        }
        // find chiTietMenu
        ChiTietMenu chiTietMenu = chiTietMenuRepo.findById(request.getMaCtMenu())
                .orElseThrow(() -> new RuntimeException("ChiTietMenu doesn't exist"));
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

        return danhSachSanPham.stream().map(
                chiTiet -> new MenuResponse (
                        chiTiet.getMaCtMenu(),
                        chiTiet.getSanPham().getMaSanPham(),
                        chiTiet.getSanPham().getTenSanPham(),
                        chiTiet.getSanPham().getMoTa(),
                        chiTiet.getSanPham().getGiaBan(),
                        chiTiet.getSanPham().getHinhAnh(),
                        chiTiet.getSanPham().getLoaiSanPham().getMaLoai(),
                        chiTiet.getSanPham().getTrangThai()
                )
        ).toList();
    }
}
