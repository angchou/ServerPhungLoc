package com.example.phungloc.impl;

import com.example.phungloc.dto.request.supplier_request.CreateSupplierRequest;
import com.example.phungloc.dto.response.supplier_response.SupplierResponse;
import com.example.phungloc.entities.NhaCungCap;
import com.example.phungloc.exception.AppException;
import com.example.phungloc.exception.ErrorCode;
import com.example.phungloc.repositories.NguyenLieuRepo;
import com.example.phungloc.repositories.NhaCungCapRepo;
import com.example.phungloc.services.SupplierService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.example.phungloc.util.UpdateHelper.updateIfChange;

@Slf4j
@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private NhaCungCapRepo nhaCungCapRepo;
    @Autowired
    private NguyenLieuRepo nguyenLieuRepo;

    @Override
    public List<SupplierResponse> getSuppliers() {
        log.info("Getting all suppliers");
        return nhaCungCapRepo.findAll().stream().map(
                ncc -> new SupplierResponse(
                        ncc.getMaNcc(),
                        ncc.getTenNcc(),
                        ncc.getSdt(),
                        ncc.getDiaChi()
                )
        ).toList();
    }

    @Override
    @Transactional
    public void createSupplier(CreateSupplierRequest request) {
        log.info("Creating new supplier: {}", request.getTenNcc());
        NhaCungCap nhaCungCap = new NhaCungCap();
        nhaCungCap.setTenNcc(request.getTenNcc().trim().replaceAll("\\s+", " "));
        nhaCungCap.setSdt(request.getSdt());
        nhaCungCap.setDiaChi(request.getDiaChi().replaceAll("\\s+", " "));
        nhaCungCapRepo.save(nhaCungCap);
        log.info("Successfully created new supplier: {}", nhaCungCap.getMaNcc());
    }

    @Override
    @Transactional
    public void deleteSupplier(String maNcc) {
        log.info("Deleting supplier: {}", maNcc);
        if (nguyenLieuRepo.existsByNhaCungCap_MaNcc(maNcc)) {
            log.info("Can not delete supplier {} because they are still provide activated ingredient(s)", maNcc);
            throw new AppException(ErrorCode.INVALID_DELETE, "Không thể xóa nhà cung cấp vì họ vẫn còn đang cung cấp nguyên liệu cho công ty!");
        }
        nhaCungCapRepo.deleteById(maNcc);
        log.info("Successfully deleted supplier: {}", maNcc);
    }

    @Override
    @Transactional
    public boolean updateSupplier(String maNcc, CreateSupplierRequest request) {
        NhaCungCap nhaCungCap = nhaCungCapRepo.findById(maNcc)
                .orElseThrow(() -> {
                    log.info("Supplier: {} not found", maNcc);
                    return new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy nhà cung cấp!");
                });

        log.info("Updating supplier: {}", maNcc);
        boolean changed = false;
        changed |= updateIfChange(nhaCungCap::getTenNcc, request.getTenNcc().trim().replaceAll("\\s+", " "), nhaCungCap::setTenNcc);
        changed |= updateIfChange(nhaCungCap::getSdt, request.getSdt(), nhaCungCap::setSdt);
        changed |= updateIfChange(nhaCungCap::getDiaChi, request.getDiaChi().trim().replaceAll("\\s+", " "), nhaCungCap::setDiaChi);

        if (changed) {
            log.info("Successfully updated supplier: {}", maNcc);
        } else {
            log.info("Nothing has changed, supplier: {}", maNcc);
        }
        return changed;
    }
}
