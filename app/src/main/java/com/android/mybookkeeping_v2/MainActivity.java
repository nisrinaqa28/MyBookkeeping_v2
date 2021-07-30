package com.android.mybookkeeping_v2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.android.mybookkeeping_v2.database.Transaksi;
import com.android.mybookkeeping_v2.database.TransaksiViewModel;
import com.android.mybookkeeping_v2.recyclerview.TransaksiAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_TRANSAKSI_REQUEST = 1;
    public static final int EDIT_TRANSAKSI_REQUEST = 2;

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private TransaksiViewModel transaksiViewModel;
    private TextView totalPemasukan, totalPengeluaran, totalSaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        TransaksiAdapter adapter = new TransaksiAdapter();
        recyclerView.setAdapter(adapter);

        transaksiViewModel = ViewModelProviders.of(this).get(TransaksiViewModel.class);
        transaksiViewModel.getAllTransaksi().observe(this, new Observer<List<Transaksi>>() {
            @Override
            public void onChanged(@Nullable List<Transaksi> transaksis) {
                adapter.submitList(transaksis);
            }
        });

        //floating button untuk menambah transaksi baru
        floatingActionButton = findViewById(R.id.pencatatanButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTambahTransaksi();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                transaksiViewModel.delete(adapter.getTransaksiAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Transaction Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TransaksiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Transaksi transaksi) {
                Intent intent = new Intent(MainActivity.this, EditTambahTransaksi.class);
                intent.putExtra(EditTambahTransaksi.EXTRA_ID, transaksi.getId());
                intent.putExtra(EditTambahTransaksi.EXTRA_JENIS, transaksi.getJenis_transaksi());
                intent.putExtra(EditTambahTransaksi.EXTRA_TANGGAL, transaksi.getTanggal());
                intent.putExtra(EditTambahTransaksi.EXTRA_KATEGORI, transaksi.getKategori());
                intent.putExtra(EditTambahTransaksi.EXTRA_JUMLAH, transaksi.getJumlah());
                intent.putExtra(EditTambahTransaksi.EXTRA_KETERANGAN, transaksi.getKeterangan());
                startActivityForResult(intent, EDIT_TRANSAKSI_REQUEST);
            }
        });

        //dashboard total pemasukan,pengeluaran dan saldo
        totalPemasukan = findViewById(R.id.pemasukan);
        totalPengeluaran = findViewById(R.id.pengeluaran);
        totalSaldo = findViewById(R.id.saldo);


    }

    public void openTambahTransaksi(){
        Intent intent = new Intent(this, EditTambahTransaksi.class);
        startActivityForResult(intent, ADD_TRANSAKSI_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TRANSAKSI_REQUEST && resultCode == RESULT_OK){
            String jenisTransaksi = data.getStringExtra(EditTambahTransaksi.EXTRA_JENIS);
            Date tanggal = (Date) data.getSerializableExtra(EditTambahTransaksi.EXTRA_TANGGAL);
            String kategori = data.getStringExtra(EditTambahTransaksi.EXTRA_KATEGORI);
            int jumlahTransaksi = data.getIntExtra(EditTambahTransaksi.EXTRA_JUMLAH, 1);
            String keterangan  = data.getStringExtra(EditTambahTransaksi.EXTRA_KETERANGAN);

            Transaksi transaksi = new Transaksi(jenisTransaksi, tanggal, kategori, jumlahTransaksi, keterangan);
            transaksiViewModel.insert(transaksi);

            Toast.makeText(MainActivity.this, "Transaction Saved", Toast.LENGTH_SHORT).show();
        }else if (requestCode == EDIT_TRANSAKSI_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(EditTambahTransaksi.EXTRA_ID, -1);

            if (id==-1){
                Toast.makeText(this,"Transaksi can't be update", Toast.LENGTH_SHORT).show();
                return;
            }

            String jenisTransaksi = data.getStringExtra(EditTambahTransaksi.EXTRA_JENIS);
            Date tanggal = (Date) data.getSerializableExtra(EditTambahTransaksi.EXTRA_TANGGAL);
            String kategori = data.getStringExtra(EditTambahTransaksi.EXTRA_KATEGORI);
            int jumlahTransaksi = data.getIntExtra(EditTambahTransaksi.EXTRA_JUMLAH, 1);
            String keterangan  = data.getStringExtra(EditTambahTransaksi.EXTRA_KETERANGAN);

            Transaksi transaksi = new Transaksi(jenisTransaksi,tanggal,kategori,jumlahTransaksi,keterangan);
            transaksi.setId(id);
            transaksiViewModel.update(transaksi);

            Toast.makeText(MainActivity.this, "Tracation Updated", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "Transaction Not Saved", Toast.LENGTH_SHORT).show();
        }
    }
}