package com.example.phungloc.repositories;

import com.example.phungloc.entities.ChiNhanh;
import com.example.phungloc.entities.NguyenLieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NguyenLieuRepo extends JpaRepository<NguyenLieu, String> {
    List<NguyenLieu> findByTrangThaiIn(List<Integer> trangThai);
}
