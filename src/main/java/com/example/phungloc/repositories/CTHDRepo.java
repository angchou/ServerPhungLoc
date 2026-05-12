package com.example.phungloc.repositories;

import com.example.phungloc.entities.CTHD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CTHDRepo extends JpaRepository<CTHD, String> {
}
