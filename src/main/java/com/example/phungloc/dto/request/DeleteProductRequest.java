package com.example.phungloc.dto.request;

public class DeleteProductRequest {
    private String maMenu;
    private String maCtMenu;

    public String getMaMenu() {
        return maMenu;
    }

    public void setMaMenu(String maMenu) {
        this.maMenu = maMenu;
    }

    public String getMaCtMenu() {
        return maCtMenu;
    }

    public void setMaCtMenu(String maCtMenu) {
        this.maCtMenu = maCtMenu;
    }
}
