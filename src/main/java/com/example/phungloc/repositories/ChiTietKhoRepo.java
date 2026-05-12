package com.example.phungloc.repositories;

import com.example.phungloc.entities.ChiTietKho;
import com.example.phungloc.entities.Kho;
import com.example.phungloc.entities.NguyenLieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChiTietKhoRepo extends JpaRepository<ChiTietKho, String> {
    Optional<ChiTietKho> findByNguyenLieu_MaNguyenLieu(String maNguyenLieu);
    Optional<ChiTietKho> findByKho_MaKhoAndNguyenLieu_MaNguyenLieu(String maKho, String maNguyenLieu);

    List<ChiTietKho> findByKho_ChiNhanh_MaChiNhanh(String maChiNhanh);
}
