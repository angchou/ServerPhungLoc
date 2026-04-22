package com.example.phungloc.repositories;

import com.example.phungloc.entities.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhanVienRepo extends JpaRepository<NhanVien, String> {
    Optional<NhanVien> findByTaiKhoan(String taiKhoan);
}
