package com.example.phungloc.repositories;

import com.example.phungloc.dto.response.emp_response.EmployeeResponse;
import com.example.phungloc.entities.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NhanVienRepo extends JpaRepository<NhanVien, String> {
    Optional<NhanVien> findByTaiKhoan(String taiKhoan);
    List<NhanVien> findByChiNhanh_MaChiNhanh(String maChiNhanh);
    boolean existsByTaiKhoan(String taiKhoan);

    @Query(
    """
        SELECT new com.example.phungloc.dto.response.emp_response.EmployeeResponse(
            nv.maNhanVien,
            nv.tenNhanVien,
            nv.taiKhoan,
            nv.matKhau,
            nv.ngayLamViec,
            nv.trangThai,
            cn.maChiNhanh,
            cn.tenChiNhanh,
            r.roleId
        )
        FROM NhanVien nv
        JOIN nv.chiNhanh cn
        JOIN UserRole ur ON ur.nhanVien.maNhanVien = nv.maNhanVien
        JOIN ur.role r
        WHERE cn.maChiNhanh = :maChiNhanh
        AND LOWER(r.roleName) = 'manager'
        ORDER BY nv.maNhanVien
    """
    )
    List<EmployeeResponse> findManagersByChiNhanh(String maChiNhanh);

    @Query(
    """
        SELECT new com.example.phungloc.dto.response.emp_response.EmployeeResponse(
            nv.maNhanVien,
            nv.tenNhanVien,
            nv.taiKhoan,
            nv.matKhau,
            nv.ngayLamViec,
            nv.trangThai,
            cn.maChiNhanh,
            cn.tenChiNhanh,
            r.roleId
        )
        FROM NhanVien nv
        JOIN nv.chiNhanh cn
        JOIN UserRole ur ON ur.nhanVien.maNhanVien = nv.maNhanVien
        JOIN ur.role r
        WHERE cn.maChiNhanh = (
            SELECT nv2.chiNhanh.maChiNhanh
            FROM NhanVien nv2
            WHERE nv2.maNhanVien = :userID
        )
        AND r.roleId <> 'R0003'
        ORDER BY nv.maNhanVien
    """
    )
    List<EmployeeResponse> findEmployeesByManager(String userID);
}
