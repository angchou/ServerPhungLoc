package com.example.phungloc.repositories;

import com.example.phungloc.entities.Kho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhoRepo extends JpaRepository<Kho, String> {
    List<Kho> findByChiNhanh_TrangThai(int trangThai);
    Optional<Kho> findByChiNhanh_MaChiNhanh(String maChiNhanh);
}
