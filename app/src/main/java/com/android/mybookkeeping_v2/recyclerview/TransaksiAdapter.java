package com.android.mybookkeeping_v2.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.android.mybookkeeping_v2.R;
import com.android.mybookkeeping_v2.database.Transaksi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransaksiAdapter extends ListAdapter<Transaksi, TransaksiAdapter.TransaksiHolder> {

    private OnItemClickListener listener;

    public TransaksiAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Transaksi> DIFF_CALLBACK = new DiffUtil.ItemCallback<Transaksi>() {
        @Override
        public boolean areItemsTheSame(Transaksi oldItem, Transaksi newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Transaksi oldItem, Transaksi newItem) {
            return oldItem.getTanggal().equals(newItem.getTanggal());
        }
    };

    @NonNull
    @Override
    public TransaksiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaksi, parent, false);
        return new TransaksiAdapter.TransaksiHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiAdapter.TransaksiHolder holder, int position) {
        Transaksi currentTransaksi = getItem(position);

        Date tanggalConvert = currentTransaksi.getTanggal();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");//formating according to my need
        String date = formatter.format(tanggalConvert);

        holder.tanggal.setText(date);
        holder.jenisTransaksi.setText(currentTransaksi.getJenis_transaksi());
        holder.kategori.setText(currentTransaksi.getKategori());
        holder.keterangan.setText(currentTransaksi.getKeterangan());
        holder.jumlahTransaksi.setText(String.valueOf(currentTransaksi.getJumlah()));
    }

    public Transaksi getTransaksiAt(int position){
        return getItem(position);
    }

    public class TransaksiHolder extends RecyclerView.ViewHolder{
        private TextView tanggal,jumlahTransaksi,kategori,keterangan,jenisTransaksi;

        public TransaksiHolder(View itemView){
            super(itemView);
            tanggal = itemView.findViewById(R.id.tanggal);
            jenisTransaksi = itemView.findViewById(R.id.jenisTransaksi);
            kategori = itemView.findViewById(R.id.kategori);
            keterangan = itemView.findViewById(R.id.keterangan);
            jumlahTransaksi = itemView.findViewById(R.id.jumlahTransaksi);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Transaksi transaksi);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}