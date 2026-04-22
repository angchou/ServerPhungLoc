package com.example.phungloc.controllers;

import com.example.phungloc.impl.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    private ImageServiceImpl imageService;

    @PostMapping("/upload/{maSanPham}")
    @PreAuthorize("hasAnyRole('REGION_MANAGER')")
    public ResponseEntity<?> uploadProductImage(@RequestParam("image") MultipartFile image, @PathVariable("maSanPham") String maSanPham) {
        return imageService.uploadProductImage(image, maSanPham);
    }

}
