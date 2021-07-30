package com.android.mybookkeeping_v2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class EditTambahTransaksi extends AppCompatActivity {

    public static final String EXTRA_ID = "com.android.mybookkeeping_v2.EXTRA_ID";
    public static final String EXTRA_JENIS = "com.android.mybookkeeping_v2.EXTRA_JENIS";
    public static final String EXTRA_TANGGAL = "com.android.mybookkeeping_v2.EXTRA_TANGGAL";
    public static final String EXTRA_KATEGORI = "com.android.mybookkeeping_v2.EXTRA_KATEGORI";
    public static final String EXTRA_JUMLAH = "com.android.mybookkeeping_v2.EXTRA_JUMLAH";
    public static final String EXTRA_KETERANGAN = "com.android.mybookkeeping_v2.EXTRA_KETERANGAN";

    EditText editDate, editJumlah, editKeterangan;
    Spinner spinner;
    Button simpanBtn;
    RadioGroup list_transaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_transaksi);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editDate = findViewById(R.id.editTextDate);
        list_transaksi = findViewById(R.id.opsiTransaksi);
        editJumlah = findViewById(R.id.editTextNumber);
        editKeterangan = findViewById(R.id.keteranganInput);
        simpanBtn = findViewById(R.id.simpanButton);
        spinner = findViewById(R.id.kategoriInput);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)){
            editDate.setText(intent.getStringExtra(EXTRA_TANGGAL));
            list_transaksi.setSelected(intent.getBooleanExtra(EXTRA_JENIS, true));
            spinner.setSelected(intent.getBooleanExtra(EXTRA_KATEGORI,true));
            editJumlah.setText(intent.getStringExtra(EXTRA_JUMLAH));
            editKeterangan.setText(intent.getStringExtra(EXTRA_KETERANGAN));
        }

        //Proses Tanggal
        Calendar calendar = Calendar.getInstance();

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditTambahTransaksi.this, (DatePickerDialog.OnDateSetListener) (view1, year1, month1, day1) -> {
                    month1 = month1 + 1;
                    String date = day1 + "/" + month1 + "/" + year1;
                    editDate.setText(date);
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //jenis transaksi
        list_transaksi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.pemasukan:
                        String[] pemasukanValue = {"Deposito","Dividen","Gaji","Hibah","Investasi","Kupon","Lain-lain",
                                "Pengembalian Dana","Penghargaan","Penjualan","Penyewaan","Tabungan"};
                        ArrayList<String> pemasukanList = new ArrayList<>(Arrays.asList(pemasukanValue));
                        ArrayAdapter<String> pemasukanAdapter = new ArrayAdapter<>(EditTambahTransaksi.this,R.layout.style_spinner,pemasukanList);
                        pemasukanAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                        spinner.setAdapter(pemasukanAdapter);
                        break;
                    case R.id.pengeluaran:
                        String[] pengeluaranValue = {"Asuransi","Bayi","Belanja","Buah-buahan","Cemilan","Elektronik",
                                "Hadiah","Hewan Peliharaan","Hiburan","Kantor","Kecantikan","Kesehatan",
                                "Lain-lain","Makanan","Mobil","Motor","Olahraga","Pajak","Pakaian","Pendidikan",
                                "Pulsa","Rokok","Rumah","Sosial","Tagihan","Taksi","Transportasi"};

                        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(pengeluaranValue));
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditTambahTransaksi.this,R.layout.style_spinner,arrayList);
                        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                        spinner.setAdapter(arrayAdapter);
                        break;
                }
            }
        });

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveTransaksi();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveTransaksi() throws ParseException {
        int selectId = list_transaksi.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectId);
        String jenisTransaksi = selectedRadioButton.getText().toString();

        Date tanggal = new SimpleDateFormat("dd/MM/yyyy").parse(editDate.getText().toString());

        String kategori = spinner.getSelectedItem().toString();

        int jumlahTransaksi = Integer.parseInt(editJumlah.getText().toString());

        String keterangan = editKeterangan.getText().toString();

        Intent data = new Intent();
        data.putExtra(EXTRA_JENIS, jenisTransaksi);
        data.putExtra(EXTRA_TANGGAL, tanggal);
        data.putExtra(EXTRA_KATEGORI, kategori);
        data.putExtra(EXTRA_JUMLAH, jumlahTransaksi);
        data.putExtra(EXTRA_KETERANGAN, keterangan);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}