package com.example.phungloc.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ResponseEntity<?> uploadProductImage(MultipartFile file, String maSanPham);
}
