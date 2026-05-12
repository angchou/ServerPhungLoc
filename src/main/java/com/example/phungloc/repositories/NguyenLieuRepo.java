package com.example.phungloc.repositories;

import com.example.phungloc.entities.ChiNhanh;
import com.example.phungloc.entities.NguyenLieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NguyenLieuRepo extends JpaRepository<NguyenLieu, String> {
    List<NguyenLieu> findByTrangThaiInOrderByMaNguyenLieu(List<Integer> trangThai);
    List<NguyenLieu> findByNhaCungCap_MaNcc(String maNcc);
    List<NguyenLieu> findByTrangThaiOrderByMaNguyenLieu(int trangThai);
    boolean existsByNhaCungCap_MaNcc(String maNcc);

    @Query(
    """
        SELECT nl FROM NguyenLieu nl
        JOIN FETCH nl.nhaCungCap
        WHERE nl.trangThai IN :trangThai
        AND nl.nhaCungCap.maNcc = :maNcc
        ORDER BY nl.maNguyenLieu
    """
    )
    List<NguyenLieu> findAllOfSupplier(List<Integer> trangThai, String maNcc);
}
