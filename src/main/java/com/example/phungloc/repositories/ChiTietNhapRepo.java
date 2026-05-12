package com.example.phungloc.repositories;

import com.example.phungloc.entities.ChiTietNhap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietNhapRepo extends JpaRepository<ChiTietNhap, String> {
}
