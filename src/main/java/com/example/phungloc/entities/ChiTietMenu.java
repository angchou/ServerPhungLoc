package com.example.phungloc.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ChiTietMenu")
public class ChiTietMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maCtMenu")
    private String maCtMenu;

    @ManyToOne
    @JoinColumn(name = "maMenu")
    private Menu menu;
    @ManyToOne
    @JoinColumn(name = "maSanPham")
    private SanPham sanPham;

    public String getMaCtMenu() {
        return maCtMenu;
    }

    public void setMaCtMenu(String maCtMenu) {
        this.maCtMenu = maCtMenu;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }
}
