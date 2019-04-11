package com.example.kelvin.movieapp2;

public class MovieItem {
    private String Judul, Deskripsi, Tanggal, Gambar;

    MovieItem(String judul, String deskripsi, String tanggal, String gambar) {
        this.Judul = judul;
        this.Deskripsi = deskripsi;
        this.Tanggal = tanggal;
        this.Gambar = gambar;
    }

    public String getJudul() {
        return Judul;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public String getGambar(){
        return Gambar;
    }
}