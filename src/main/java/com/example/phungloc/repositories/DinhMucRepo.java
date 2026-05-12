package com.example.phungloc.repositories;

import com.example.phungloc.entities.DinhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DinhMucRepo extends JpaRepository<DinhMuc, String> {
    List<DinhMuc> findByCongThuc_MaCongThuc(String maCongThuc);
}
