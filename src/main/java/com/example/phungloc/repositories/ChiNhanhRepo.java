package com.example.phungloc.repositories;

import com.example.phungloc.entities.ChiNhanh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiNhanhRepo extends JpaRepository<ChiNhanh, String> {
    List<ChiNhanh> findByTrangThaiIn(List<Integer> trangThai);
}
