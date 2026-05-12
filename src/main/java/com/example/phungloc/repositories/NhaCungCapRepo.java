package com.example.phungloc.repositories;

import com.example.phungloc.entities.NhaCungCap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhaCungCapRepo extends JpaRepository<NhaCungCap, String> {
}
