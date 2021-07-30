package com.android.mybookkeeping_v2.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "pencatatan")
public class Transaksi {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String jenis_transaksi;

    @TypeConverters({DateTypeConvertor.class})
    private Date  tanggal;

    private String kategori;

    private int jumlah;

    private String keterangan;

    public Transaksi(String jenis_transaksi, Date tanggal, String kategori, int jumlah, String keterangan) {
        this.jenis_transaksi = jenis_transaksi;
        this.tanggal = tanggal;
        this.kategori = kategori;
        this.jumlah = jumlah;
        this.keterangan = keterangan;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getJenis_transaksi() {
        return jenis_transaksi;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public String getKategori() {
        return kategori;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getKeterangan() {
        return keterangan;
    }
}
