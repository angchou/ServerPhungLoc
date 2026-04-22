package com.example.phungloc.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Kho")
public class Kho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maKho")
    private String maKho;

    @OneToOne
    @JoinColumn(name = "maChiNhanh")
    private ChiNhanh chiNhanh;

    public String getMaKho() {
        return maKho;
    }

    public void setMaKho(String maKho) {
        this.maKho = maKho;
    }

    public ChiNhanh getChiNhanh() {
        return chiNhanh;
    }

    public void setChiNhanh(ChiNhanh chiNhanh) {
        this.chiNhanh = chiNhanh;
    }
}
