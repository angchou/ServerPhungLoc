package com.example.phungloc.repositories;

import com.example.phungloc.entities.PhieuNhapKho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuNhapKhoRepo extends JpaRepository<PhieuNhapKho, String> {
}
