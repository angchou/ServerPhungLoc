package com.example.phungloc.impl;

import com.example.phungloc.dto.request.recipe_request.CreateRecipeRequest;
import com.example.phungloc.dto.request.recipe_request.RecipeDetailRequest;
import com.example.phungloc.dto.response.recipe_response.RecipeDetailResponse;
import com.example.phungloc.dto.response.recipe_response.RecipeResponse;
import com.example.phungloc.dto.response.recipe_response.SizeResponse;
import com.example.phungloc.entities.*;
import com.example.phungloc.exception.AppException;
import com.example.phungloc.exception.ErrorCode;
import com.example.phungloc.projection.RecipeProjection;
import com.example.phungloc.repositories.*;
import com.example.phungloc.services.RecipeService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import static com.example.phungloc.util.UpdateHelper.updateIfChange;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private KichCoRepo kichCoRepo;
    @Autowired
    private CongThucRepo congThucRepo;
    @Autowired
    private NguyenLieuRepo nguyenLieuRepo;
    @Autowired
    private DinhMucRepo dinhMucRepo;
    @Autowired
    private SanPhamRepo sanPhamRepo;

    @Override
    public List<SizeResponse> getSizes() {
        log.info("Getting product sizes");
        return kichCoRepo.findAll().stream().map(
                size -> new SizeResponse(
                        size.getMaKichCo(),
                        size.getTenKichCo()
                )
        ).toList();
    }

    @Override
    public RecipeResponse getRecipe(String maKichCo, String maSanPham) {
        log.info("Getting recipe of product: {} with size: {}", maKichCo, maKichCo);
        List<RecipeProjection> list =
                congThucRepo.findRecipe(maKichCo, maSanPham);

        if (list.isEmpty()) {
            log.info("Recipe doesn't exist");
            return new RecipeResponse(null, null, null, List.of());
        }

        RecipeProjection first = list.get(0);
        List<RecipeDetailResponse> danhSachDinhMuc = list.stream()
                .map(p -> new RecipeDetailResponse(
                        p.getMaDinhMuc(),
                        p.getSoLuong(),
                        p.getMaNguyenLieu(),
                        p.getTenNguyenLieu(),
                        p.getDonVi(),
                        p.getGiaBan(),
                        p.getTrangThai(),
                        p.getMaNcc(),
                        p.getTenNcc()
                ))
                .toList();

        return new RecipeResponse(
                first.getMaCongThuc(),
                first.getNgayTao(),
                first.getGiaUpsize(),
                danhSachDinhMuc
        );
    }

    @Override
    @Transactional
    public void createRecipe(CreateRecipeRequest request) {
        log.info("Creating new recipe for product: {} with size: {}", request.getMaSanPham(), request.getMaKichCo());
        if (congThucRepo.existsByKichCo_MaKichCoAndSanPham_MaSanPham(request.getMaKichCo(), request.getMaSanPham())) {
            throw new AppException(ErrorCode.INVALID_CREATE, "Đã tồn tại công thức này rồi, không thể tạo thêm!");
        }
        SanPham sanPham = sanPhamRepo.findById(request.getMaSanPham())
                .orElseThrow(() -> {
                    log.info("Product: {} not found", request.getMaSanPham());
                    return new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy sản phẩm " + request.getMaSanPham());
                });
        KichCo kichCo = kichCoRepo.findById(request.getMaKichCo())
                .orElseThrow(() -> {
                    log.info("Missing size: {}", request.getMaKichCo());
                    return new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy kích cỡ " + request.getMaKichCo());
                });
        CongThuc congThuc = new CongThuc();
        congThuc.setNgayTao(LocalDate.now());
        congThuc.setSanPham(sanPham);
        congThuc.setKichCo(kichCo);
        congThuc.setGiaUpsize(request.getGiaUpsize());
        congThucRepo.save(congThuc);
        log.info("Successfully created recipe: {}", congThuc.getMaCongThuc());

        List<String> danhSachIds = request.getDanhSachDinhMuc()
                .stream()
                .map(RecipeDetailRequest::getMaNguyenLieu)
                .toList();
        Map<String, NguyenLieu> mapNguyenLieu = nguyenLieuRepo.findAllById(danhSachIds)
                .stream()
                .collect(Collectors.toMap(NguyenLieu::getMaNguyenLieu, nl -> nl));

        List<DinhMuc> danhSachDinhMuc = request.getDanhSachDinhMuc()
                .stream()
                .map(ct -> {
                    NguyenLieu nl = mapNguyenLieu.get(ct.getMaNguyenLieu());
                    if (nl == null) {
                        throw new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy nguyên liệu " + ct.getMaNguyenLieu());
                    }
                    DinhMuc dm = new DinhMuc();
                    dm.setCongThuc(congThuc);
                    dm.setSoLuong(ct.getSoLuong());
                    dm.setNguyenLieu(nl);
                    return dm;
                })
                .toList();

        dinhMucRepo.saveAll(danhSachDinhMuc);
        log.info("Successfully created new recipe's ingredients");
    }

    @Override
    @Transactional
    public boolean updateRecipe(String maCongThuc, CreateRecipeRequest request) {
        CongThuc congThuc = congThucRepo.findById(maCongThuc)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy công thức!"));

        log.info("Updating recipe: {}", maCongThuc);
        boolean changed = false;
        congThuc.setNgayTao(LocalDate.now());
        changed |= updateIfChange(congThuc::getGiaUpsize, request.getGiaUpsize(), congThuc::setGiaUpsize);

        List<DinhMuc> dinhMucCu = dinhMucRepo.findByCongThuc_MaCongThuc(maCongThuc);
        Map<String, DinhMuc> mapDinhMucCu = dinhMucCu.stream()
                .collect(Collectors.toMap(DinhMuc::getMaDinhMuc, dm -> dm));

        Set<String> dinhMucMoiIds = request.getDanhSachDinhMuc().stream()
                .map(RecipeDetailRequest::getMaDinhMuc)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<DinhMuc> toDelete = dinhMucCu.stream()
                .filter(dm -> !dinhMucMoiIds.contains(dm.getMaDinhMuc()))
                .toList();
        dinhMucRepo.deleteAll(toDelete);

        List<String> nguyenLieuIds = request.getDanhSachDinhMuc().stream()
                .map(RecipeDetailRequest::getMaNguyenLieu)
                .toList();
        Map<String, NguyenLieu> mapNguyenLieu = nguyenLieuRepo.findAllById(nguyenLieuIds)
                .stream()
                .collect(Collectors.toMap(NguyenLieu::getMaNguyenLieu, nl -> nl));

        List<DinhMuc> toSave = new ArrayList<>();
        for (RecipeDetailRequest ct : request.getDanhSachDinhMuc()) {
            if (ct.getMaDinhMuc() != null) {
                DinhMuc dm = mapDinhMucCu.get(ct.getMaDinhMuc());
                if (dm == null) {
                    log.info("Can not found recipe detail: {}", ct.getMaDinhMuc());
                    throw new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy định mức!");
                }
                changed |= updateIfChange(dm::getSoLuong, ct.getSoLuong(), dm::setSoLuong);
            }
            else {
                NguyenLieu nl = mapNguyenLieu.get(ct.getMaNguyenLieu());
                if (nl == null) {
                    throw new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy nguyên liệu!");
                }
                DinhMuc dm = new DinhMuc();
                dm.setCongThuc(congThuc);
                dm.setNguyenLieu(nl);
                dm.setSoLuong(ct.getSoLuong());
                toSave.add(dm);
            }
        }
        if (!toSave.isEmpty() || !toDelete.isEmpty()) {
            changed = true;
        }
        dinhMucRepo.saveAll(toSave);
        log.info("Successfully updated recipe!");
        return changed;
    }

    @Override
    public void deleteRecipe(String maCongThuc) {
        log.info("Deleting recipe: {}", maCongThuc);
        congThucRepo.deleteById(maCongThuc);
        log.info("Successfully deleted recipe: {}", maCongThuc);
    }
}
