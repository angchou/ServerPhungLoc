package com.example.phungloc.impl;

import com.example.phungloc.entities.SanPham;
import com.example.phungloc.repositories.SanPhamRepo;
import com.example.phungloc.services.ImageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class ImageServiceImpl implements ImageService {
    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private SanPhamRepo sanPhamRepo;

    @Override
    @Transactional
    public ResponseEntity<?> uploadProductImage(MultipartFile file, String maSanPham) {
        try {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + filename);
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path);

            // find SanPham
            SanPham sanPham = sanPhamRepo.findById(maSanPham)
                    .orElseThrow(() -> new RuntimeException("Product doesn't exist!"));
            // delete old image
            if (sanPham.getHinhAnh() != null) {
                String oldFilename = sanPham.getHinhAnh().replace("/uploads/", "");
                Path oldPath = Paths.get(UPLOAD_DIR, oldFilename);
                Files.deleteIfExists(oldPath);
            }
            // add new hinhAnh
            sanPham.setHinhAnh("/" + UPLOAD_DIR + filename);
            sanPhamRepo.save(sanPham);

            return ResponseEntity.ok().body("Successfully uploaded image!");
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
