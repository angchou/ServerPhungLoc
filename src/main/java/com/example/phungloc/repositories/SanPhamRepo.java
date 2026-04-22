package com.example.phungloc.repositories;

import com.example.phungloc.entities.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepo extends JpaRepository<SanPham, String> {
    List<SanPham> findByTrangThai(Integer trangThai);
}
