package com.example.phungloc.repositories;

import com.example.phungloc.entities.LoaiSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiSanPhamRepo extends JpaRepository<LoaiSanPham, String> {
}
