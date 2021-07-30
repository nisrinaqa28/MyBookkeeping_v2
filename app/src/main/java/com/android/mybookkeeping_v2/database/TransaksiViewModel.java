package com.android.mybookkeeping_v2.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class TransaksiViewModel extends AndroidViewModel {

    private TransaksiRepository repository;
    private LiveData<List<Transaksi>> allTransaksi;

    public TransaksiViewModel(@NonNull Application application) {
        super(application);
        repository = new TransaksiRepository(application);
        allTransaksi=repository.getAllTransaksi();
    }

    public void insert(Transaksi transaksi){
        repository.insert(transaksi);
    }

    public void update(Transaksi transaksi){
        repository.update(transaksi);
    }

    public void delete(Transaksi transaksi){
        repository.delete(transaksi);
    }

    public LiveData<List<Transaksi>> getAllTransaksi(){
        return allTransaksi;
    }
}
