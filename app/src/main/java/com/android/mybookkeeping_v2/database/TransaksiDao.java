package com.android.mybookkeeping_v2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransaksiDao {

    @Insert
    void insert (Transaksi transaksi);

    @Update
    void update (Transaksi transaksi);

    @Delete
    void delete (Transaksi transaksi);

    @Query("SELECT * FROM pencatatan ORDER BY tanggal DESC")
    LiveData<List<Transaksi>>getAllTransaksi();

    @Query("SELECT SUM(jumlah) FROM pencatatan WHERE jenis_transaksi == 'Pemasukan'")
    int getJumlahPemasukan();

    @Query("SELECT SUM(jumlah) FROM pencatatan WHERE jenis_transaksi == 'Pengeluaran'")
    int getJumlahPengeluaran();
}