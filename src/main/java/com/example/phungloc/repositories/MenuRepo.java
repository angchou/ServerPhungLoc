package com.example.phungloc.repositories;

import com.example.phungloc.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepo extends JpaRepository<Menu, String> {
}
