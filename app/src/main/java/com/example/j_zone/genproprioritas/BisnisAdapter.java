package com.example.j_zone.genproprioritas;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public void onBindViewHolder(@NonNull BisnisAdapter.ViewHolder holder, int position) {
        Bisnis bisnis = list.get(position);
        holder.txtBisnis.setText(bisnis.getNmbisnislain());
        holder.txtUsaha.setText(bisnis.getNmusaha());
        holder.txtTgl.setText(bisnis.getTglTerdaftar());
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
