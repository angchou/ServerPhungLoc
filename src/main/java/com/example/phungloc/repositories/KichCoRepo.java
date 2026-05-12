package com.example.phungloc.repositories;

import com.example.phungloc.entities.KichCo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KichCoRepo extends JpaRepository<KichCo, String> {
    Optional<KichCo> findByTenKichCo(String tenKichCo);
}
