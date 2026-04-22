package com.example.phungloc.repositories;

import com.example.phungloc.entities.ChiTietMenu;
import com.example.phungloc.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietMenuRepo extends JpaRepository<ChiTietMenu, String> {
    List<ChiTietMenu> findByMenu(Menu menu);
}
