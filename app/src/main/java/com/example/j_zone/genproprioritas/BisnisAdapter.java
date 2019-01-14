package com.example.j_zone.genproprioritas;

/**
 * Created by Muhammad on 12/9/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BisnisAdapter extends RecyclerView.Adapter<BisnisAdapter.ViewHolder> {
    private Context context;
    private List<Bisnis> list;

    public BisnisAdapter(Context context, List<Bisnis> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public BisnisAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_layout_bisnis, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BisnisAdapter.ViewHolder holder, final int position) {
        Bisnis bisnis = list.get(position);
        holder.txtBisnis.setText(bisnis.getNm_usaha());
        holder.txtUsaha.setText(bisnis.getMerk());
        holder.txtTgl.setText(bisnis.getTglTerdaftar());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(context,Detailbisnis.class);

                a.putExtra("position",position);
//                Toast.makeText(context, "position "+position, Toast.LENGTH_SHORT).show();
                String name = list.get(position).getNmbisnislain();
                String usaha = list.get(position).getNm_usaha();

                String merek = list.get(position).getMerk();
                String karyawan = list.get(position).getJumlahkaryawan();
                String cabang = list.get(position).getJumlahcabang();
                String omset = list.get(position).getOmsettahunan();
                String telpon = list.get(position).getTelepon();
                String fb = list.get(position).getFacebooks();
                String ig = list.get(position).getInstagrams();

                String idbisnis = list.get(position).getId_bisnis_info();

                Toast.makeText(context, "bisnis id "+idbisnis, Toast.LENGTH_SHORT).show();

                a.putExtra("id_bisnis_info",idbisnis);
                a.putExtra("nm_bisnis_lain",name);
                a.putExtra("nm_usaha",usaha);
                a.putExtra("merk",merek);
                a.putExtra("jml_karyawan",karyawan);
                a.putExtra("jml_cabang",cabang);
                a.putExtra("omset_tahunan",omset);
                a.putExtra("no_tlp",telpon);
                a.putExtra("facebook",fb);
                a.putExtra("instagram",ig);

                context.startActivity(a);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtBisnis, txtUsaha, txtTgl;
        public ViewHolder(View itemView) {
            super(itemView);
            txtBisnis = itemView.findViewById(R.id.main_bisnis);
            txtUsaha = itemView.findViewById(R.id.main_usaha);
            txtTgl = itemView.findViewById(R.id.main_tgl);
        }
    }
}
