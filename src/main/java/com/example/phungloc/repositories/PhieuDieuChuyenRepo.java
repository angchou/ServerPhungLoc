package com.example.phungloc.repositories;

import com.example.phungloc.entities.PhieuDieuChuyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuDieuChuyenRepo extends JpaRepository<PhieuDieuChuyen, String> {
}
