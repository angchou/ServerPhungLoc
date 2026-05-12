package com.example.phungloc.impl;

import com.example.phungloc.dto.request.warehouse_request.CreateStockReceiptDetailRequest;
import com.example.phungloc.dto.response.warehouse_response.WarehouseDetailResponse;
import com.example.phungloc.entities.*;
import com.example.phungloc.repositories.*;
import com.example.phungloc.services.WarehouseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private ChiTietKhoRepo chiTietKhoRepo;
    @Autowired
    private NhanVienRepo nhanVienRepo;
    @Autowired
    private KhoRepo khoRepo;
    @Autowired
    private PhieuNhapKhoRepo phieuNhapKhoRepo;
    @Autowired
    private PhieuDieuChuyenRepo phieuDieuChuyenRepo;
    @Autowired
    private NguyenLieuRepo nguyenLieuRepo;
    @Autowired
    private ChiTietNhapRepo chiTietNhapRepo;
    @Autowired
    private ChiTietDCRepo chiTietDCRepo;
    @Autowired
    private ChiNhanhRepo chiNhanhRepo;

    @Override
    public List<WarehouseDetailResponse> getWarehouseDetail() {
        String maNhanVien = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        NhanVien nhanVien = nhanVienRepo.findById(maNhanVien).get();
        List<ChiTietKho> danhSachTonKho = chiTietKhoRepo.findByKho_ChiNhanh_MaChiNhanh(nhanVien.getChiNhanh().getMaChiNhanh());
        return danhSachTonKho.stream().map(
                chiTiet -> new WarehouseDetailResponse(
                        chiTiet.getMaTonKho(),
                        chiTiet.getSoLuong(),
                        chiTiet.getNguyenLieu().getMaNguyenLieu(),
                        chiTiet.getNguyenLieu().getTenNguyenLieu(),
                        chiTiet.getNguyenLieu().getDonVi(),
                        chiTiet.getNguyenLieu().getGiaBan(),
                        chiTiet.getNguyenLieu().getTrangThai(),
                        chiTiet.getNguyenLieu().getNhaCungCap().getMaNcc(),
                        chiTiet.getNguyenLieu().getNhaCungCap().getTenNcc(),
                        chiTiet.getNguyenLieu().getNhaCungCap().getSdt(),
                        chiTiet.getNguyenLieu().getNhaCungCap().getDiaChi()
                )
        ).toList();
    }

    @Override
    @Transactional
    public ResponseEntity<?> createStockReceipt(List<CreateStockReceiptDetailRequest> danhSach) {
        String userID = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        NhanVien nhanVien = nhanVienRepo.findById(userID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Kho kho = khoRepo.findByChiNhanh_MaChiNhanh(nhanVien.getChiNhanh().getMaChiNhanh())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        PhieuNhapKho phieuNhapKho = new PhieuNhapKho();
        phieuNhapKho.setNhanVien(nhanVien);
        phieuNhapKho.setKho(kho);
        phieuNhapKho.setNgayTao(LocalDate.now());
        phieuNhapKhoRepo.save(phieuNhapKho);

        BigDecimal tongTriGia = BigDecimal.ZERO;

        for (CreateStockReceiptDetailRequest chiTiet : danhSach) {
            if (chiTiet.getSoLuong() == null
                    || chiTiet.getSoLuong().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            ChiTietNhap chiTietNhap = new ChiTietNhap();
            NguyenLieu nguyenLieu = nguyenLieuRepo.findById(chiTiet.getMaNguyenLieu())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            if (nguyenLieu.getTrangThai() == 0) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
            }
            chiTietNhap.setPhieuNhapKho(phieuNhapKho);
            chiTietNhap.setNguyenLieu(nguyenLieu);
            chiTietNhap.setSoLuong(chiTiet.getSoLuong());
            chiTietNhapRepo.save(chiTietNhap);

            ChiTietKho chiTietKho = chiTietKhoRepo
                    .findByKho_MaKhoAndNguyenLieu_MaNguyenLieu(kho.getMaKho(), nguyenLieu.getMaNguyenLieu())
            .orElseGet(() -> {
                ChiTietKho newCTK = new ChiTietKho();
                newCTK.setKho(kho);
                newCTK.setNguyenLieu(nguyenLieu);
                newCTK.setSoLuong(BigDecimal.ZERO);
                return newCTK;
            });

            tongTriGia = tongTriGia.add(
                    chiTiet.getSoLuong().multiply(nguyenLieu.getGiaBan())
            );

            BigDecimal soLuongMoi = chiTietKho.getSoLuong().add(chiTiet.getSoLuong());
            chiTietKho.setSoLuong(soLuongMoi);
            chiTietKhoRepo.save(chiTietKho);
        }
        phieuNhapKho.setTriGia(tongTriGia);

        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<?> createStockOutReceipt(String maChiNhanh, List<CreateStockReceiptDetailRequest> danhSach) {
        String maNhanVien = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        NhanVien nhanVien = nhanVienRepo.findById(maNhanVien)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ChiNhanh chiNhanhNhan = chiNhanhRepo.findById(maChiNhanh)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Kho kho = khoRepo.findByChiNhanh_MaChiNhanh(nhanVien.getChiNhanh().getMaChiNhanh())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (chiNhanhNhan.getMaChiNhanh().equals(nhanVien.getChiNhanh().getMaChiNhanh())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        PhieuDieuChuyen phieuDieuChuyen = new PhieuDieuChuyen();
        phieuDieuChuyen.setNhanVien(nhanVien);
        phieuDieuChuyen.setChiNhanhGui(nhanVien.getChiNhanh());
        phieuDieuChuyen.setChiNhanhNhan(chiNhanhNhan);
        phieuDieuChuyen.setNgayTao(LocalDate.now());
        phieuDieuChuyenRepo.save(phieuDieuChuyen);

        BigDecimal tongTriGia = BigDecimal.ZERO;

        for (CreateStockReceiptDetailRequest chiTiet : danhSach) {
            if (chiTiet.getSoLuong() == null
                    || chiTiet.getSoLuong().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            ChiTietDC chiTietDC = new ChiTietDC();
            NguyenLieu nguyenLieu = nguyenLieuRepo.findById(chiTiet.getMaNguyenLieu())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            if (nguyenLieu.getTrangThai() == 0) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
            }
            chiTietDC.setPhieuDieuChuyen(phieuDieuChuyen);
            chiTietDC.setNguyenLieu(nguyenLieu);
            chiTietDC.setSoLuong(chiTiet.getSoLuong());
            chiTietDCRepo.save(chiTietDC);

            ChiTietKho chiTietKho = chiTietKhoRepo.findByKho_MaKhoAndNguyenLieu_MaNguyenLieu(kho.getMaKho(), nguyenLieu.getMaNguyenLieu())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            if (chiTietKho.getSoLuong().compareTo(chiTiet.getSoLuong()) < 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Không đủ hàng trong kho"
                );
            }
            tongTriGia = tongTriGia.add(
                    chiTiet.getSoLuong().multiply(nguyenLieu.getGiaBan())
            );

            BigDecimal soLuongMoi = chiTietKho.getSoLuong().subtract(chiTiet.getSoLuong());
            chiTietKho.setSoLuong(soLuongMoi);
            chiTietKhoRepo.save(chiTietKho);
        }
        phieuDieuChuyen.setTriGia(tongTriGia);

        return ResponseEntity.ok().build();
    }
}
