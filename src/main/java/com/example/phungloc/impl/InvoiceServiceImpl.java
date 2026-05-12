package com.example.phungloc.impl;

import com.example.phungloc.dto.request.invoice_request.CreateInvoiceRequest;
import com.example.phungloc.dto.request.invoice_request.InvoiceDetailRequest;
import com.example.phungloc.entities.*;
import com.example.phungloc.repositories.*;
import com.example.phungloc.services.InvoiceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private HoaDonRepo hoaDonRepo;
    @Autowired
    private CTHDRepo cthdRepo;
    @Autowired
    private ChiNhanhRepo chiNhanhRepo;
    @Autowired
    private SanPhamRepo sanPhamRepo;
    @Autowired
    private KichCoRepo kichCoRepo;
    @Autowired
    private NhanVienRepo nhanVienRepo;
    @Autowired
    private CongThucRepo congThucRepo;
    @Autowired
    private DinhMucRepo dinhMucRepo;
    @Autowired
    private KhoRepo khoRepo;
    @Autowired
    private ChiTietKhoRepo chiTietKhoRepo;

    @Override
    @Transactional
    public ResponseEntity<?> syncInvoice(CreateInvoiceRequest request) {
        if (hoaDonRepo.existsByUuid(request.getUuid())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This invoice is already saved!");
        }

        // Lấy thông tin cơ bản
        String maNhanVien = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        NhanVien nhanVien = nhanVienRepo.findById(maNhanVien)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        ChiNhanh chiNhanh = chiNhanhRepo.findById(request.getMaChiNhanh())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

        // Tìm kho của chi nhánh
        Kho kho = khoRepo.findByChiNhanh_MaChiNhanh(request.getMaChiNhanh())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found"));

        HoaDon hoaDon = new HoaDon();
        hoaDon.setUuid(request.getUuid());
        hoaDon.setNgayTao(request.getNgayTao());
        hoaDon.setChiNhanh(chiNhanh);
        hoaDon.setNhanVien(nhanVien);
        hoaDon.setTongTien(request.getTriGia());
        hoaDonRepo.save(hoaDon);

        for (InvoiceDetailRequest chiTiet : request.getChiTietHoaDon()) {
            SanPham sanPham = sanPhamRepo.findById(chiTiet.getMaSanPham())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            KichCo kichCo = kichCoRepo.findById(chiTiet.getMaKichCo())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            CTHD cthd = new CTHD();
            cthd.setHoaDon(hoaDon);
            cthd.setSanPham(sanPham);
            cthd.setKichCo(kichCo);
            cthd.setGiaSanPham(chiTiet.getGiaBan().add(chiTiet.getGiaUpsize()));
            cthd.setSoLuong(chiTiet.getSoLuong());

            cthdRepo.save(cthd);

            CongThuc congThuc = congThucRepo.findByKichCo_MaKichCoAndSanPham_MaSanPham(chiTiet.getMaKichCo(), chiTiet.getMaSanPham())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found for product"));

            List<DinhMuc> danhSachDinhMuc = dinhMucRepo.findByCongThuc_MaCongThuc(congThuc.getMaCongThuc());

            for (DinhMuc dinhMuc : danhSachDinhMuc) {
                ChiTietKho chiTietKho = chiTietKhoRepo.findByKho_MaKhoAndNguyenLieu_MaNguyenLieu(kho.getMaKho(), dinhMuc.getNguyenLieu().getMaNguyenLieu())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient not found in warehouse"));

                BigDecimal tongLuongCanTru = dinhMuc.getSoLuong().multiply(new BigDecimal(chiTiet.getSoLuong()));

                BigDecimal tonKhoMoi = chiTietKho.getSoLuong().subtract(tongLuongCanTru);

                if (tonKhoMoi.compareTo(BigDecimal.ZERO) < 0) {
                    chiTietKho.setSoLuong(BigDecimal.ZERO);
                } else {
                    chiTietKho.setSoLuong(tonKhoMoi);
                }

                chiTietKhoRepo.save(chiTietKho);
            }
        }
        return ResponseEntity.ok("Sync successful");
    }

}
