package com.example.phungloc.repositories;

import com.example.phungloc.entities.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonRepo extends JpaRepository<HoaDon, String> {
    boolean existsByUuid(String uuid);
}
