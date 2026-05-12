package com.example.phungloc.repositories;

import com.example.phungloc.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, String> {
    Optional<UserRole> findByNhanVien_MaNhanVien(String maNhanVien);

    @Modifying
    @Query(
    """
        UPDATE UserRole ur
        SET ur.role.id = :roleId
        WHERE ur.nhanVien.maNhanVien = :maNhanVien
        AND ur.role.id <> :roleId
    """
    )
    int updateRole(String maNhanVien, String roleId);
}
