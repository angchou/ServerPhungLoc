package com.example.phungloc.repositories;

import com.example.phungloc.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, String> {
    UserRole findByNhanVien_MaNhanVien(String maNhanVien);
}
