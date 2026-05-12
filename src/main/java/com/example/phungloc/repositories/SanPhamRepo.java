package com.example.phungloc.repositories;

import com.example.phungloc.entities.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepo extends JpaRepository<SanPham, String> {
    List<SanPham> findByTrangThaiOrderByMaSanPham(Integer trangThai);
    Integer countByLoaiSanPham_MaLoai(String maLoai);
    boolean existsByLoaiSanPham_MaLoai(String maLoai);

    @Query(
    """
        SELECT sp FROM SanPham sp
        JOIN FETCH sp.loaiSanPham lsp
        WHERE sp.trangThai = :trangThai
        ORDER BY sp.maSanPham
    """
    )
    List<SanPham> findAllWithLoaiSanPhamByTrangThai(int trangThai);
}
