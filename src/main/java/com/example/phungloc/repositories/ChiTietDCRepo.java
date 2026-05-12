package com.example.phungloc.repositories;

import com.example.phungloc.entities.ChiTietDC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietDCRepo extends JpaRepository<ChiTietDC, String> {

}
