package com.android.mybookkeeping_v2.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import java.util.List;

public class TransaksiRepository {
    private TransaksiDao transaksiDao;
    private LiveData<List<Transaksi>> allTransaksi;

    public TransaksiRepository (Application application){
        TransaksiDatabase database = TransaksiDatabase.getInstance(application);
        transaksiDao = database.transaksiDao();
        allTransaksi = transaksiDao.getAllTransaksi();
    }

    public void insert (Transaksi transaksi){
        new InsertTransaksiAsyncTask(transaksiDao).execute(transaksi);
    }

    public void update (Transaksi transaksi){
        new UpdateTransaksiAsyncTask(transaksiDao).execute(transaksi);
    }

    public void delete (Transaksi transaksi){
        new DeleteTransaksiAsyncTask(transaksiDao).execute(transaksi);
    }

    public LiveData<List<Transaksi>> getAllTransaksi(){
        return allTransaksi;
    }

    private static class DeleteTransaksiAsyncTask extends AsyncTask<Transaksi, Void, Void> {
        private TransaksiDao transaksiDao;

        private DeleteTransaksiAsyncTask (TransaksiDao transaksiDao){
            this.transaksiDao=transaksiDao;
        }

        @Override
        protected Void doInBackground(Transaksi... transaksis) {
            transaksiDao.delete(transaksis[0]);
            return null;
        }
    }

    private static class UpdateTransaksiAsyncTask extends AsyncTask<Transaksi, Void, Void> {
        private TransaksiDao transaksiDao;

        private UpdateTransaksiAsyncTask (TransaksiDao transaksiDao){
            this.transaksiDao=transaksiDao;
        }

        @Override
        protected Void doInBackground(Transaksi... transaksis) {
            transaksiDao.update(transaksis[0]);
            return null;
        }
    }

    private static class InsertTransaksiAsyncTask extends AsyncTask<Transaksi, Void, Void> {
        private TransaksiDao transaksiDao;

        private InsertTransaksiAsyncTask (TransaksiDao transaksiDao){
            this.transaksiDao=transaksiDao;
        }

        @Override
        protected Void doInBackground(Transaksi... transaksis) {
            transaksiDao.insert(transaksis[0]);
            return null;
        }
    }
}
