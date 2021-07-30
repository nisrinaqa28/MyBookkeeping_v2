package com.android.mybookkeeping_v2.database;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;

@Database(entities = {Transaksi.class}, version = 1)
@TypeConverters({DateTypeConvertor.class})
public abstract class TransaksiDatabase extends RoomDatabase {

    private static TransaksiDatabase instance;

    public abstract TransaksiDao transaksiDao();

    public static synchronized TransaksiDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TransaksiDatabase.class, "pencatatan")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
       private TransaksiDao transaksiDao;

       private PopulateDbAsyncTask(TransaksiDatabase db){
           transaksiDao = db.transaksiDao();
       }

        @Override
        protected Void doInBackground(Void... voids) {
           return null;
        }
    }
}
