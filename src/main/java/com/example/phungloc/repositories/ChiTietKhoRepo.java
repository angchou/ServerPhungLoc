package com.example.phungloc.repositories;

import com.example.phungloc.entities.ChiTietKho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChiTietKhoRepo extends JpaRepository<ChiTietKho, String> {
    Optional<ChiTietKho> findByNguyenLieu_MaNguyenLieu(String maNguyenLieu);
}
