package com.example.phungloc.repositories;

import com.example.phungloc.dto.response.menu_response.SizeResponse;
import com.example.phungloc.entities.CongThuc;
import com.example.phungloc.projection.RecipeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CongThucRepo extends JpaRepository<CongThuc, String> {
    List<CongThuc> findBySanPham_MaSanPham(String maSanPham);
    Optional<CongThuc> findByKichCo_MaKichCoAndSanPham_MaSanPham(String maKichCo, String maSanPham);

    @Query("SELECT new com.example.phungloc.dto.response.menu_response.SizeResponse(ct.kichCo.maKichCo, ct.kichCo.tenKichCo, ct.giaUpsize) " +
            "FROM CongThuc ct " +
            "WHERE ct.sanPham.maSanPham = :maSanPham")
    List<SizeResponse> findKichCoBySanPham(@Param("maSanPham") String maSanPham);

    boolean existsByKichCo_MaKichCoAndSanPham_MaSanPham(String maKichCo, String maSanPham);

    @Query
    ("""
        SELECT\s
            ct.maCongThuc AS maCongThuc,
            ct.ngayTao AS ngayTao,
            ct.giaUpsize AS giaUpsize,

            dm.maDinhMuc AS maDinhMuc,
            dm.soLuong AS soLuong,

            nl.maNguyenLieu AS maNguyenLieu,
            nl.tenNguyenLieu AS tenNguyenLieu,
            nl.donVi AS donVi,
            nl.giaBan AS giaBan,
            nl.trangThai AS trangThai,

            ncc.maNcc AS maNcc,
            ncc.tenNcc AS tenNcc
        FROM CongThuc ct
        JOIN ct.danhSachDinhMuc dm
        JOIN dm.nguyenLieu nl
        JOIN nl.nhaCungCap ncc
        WHERE ct.kichCo.maKichCo = :maKichCo
        AND ct.sanPham.maSanPham = :maSanPham
    """)
    List<RecipeProjection> findRecipe(String maKichCo, String maSanPham);
}
