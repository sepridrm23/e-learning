package com.alot.elearning;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ClassViewAdapter extends RecyclerView.Adapter<ClassViewAdapter.DataObjectHolder>{
    Context mContext;
    private List<Class> mListData;
    View view;

    public ClassViewAdapter(Context mContext, List<Class> mListData) {
        this.mContext = mContext;
        this.mListData = mListData;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_class, parent, false);
        return new ClassViewAdapter.DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        final Class data = mListData.get(position);
        holder.tvKelas.setText(data.getNama_kelas());
        holder.tvKuota.setText(data.getJumlah_kelas()+" kelas ("+ data.getJumlah_kuota()+" orang)\n"+"Dimulai pada tanggal "+data.getTgl_mulai());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date_now = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                Date date_mulai = new Date();
                try {
                    date_mulai = format.parse(data.getTgl_mulai());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long output_mulai=date_mulai.getTime()/1000L;
                output_mulai = output_mulai * 1000;
                long output_sebelum = output_mulai - 604800000;

                long output_now=date_now.getTime()/1000L;
                output_now = output_now * 1000;

                if (output_now >= output_sebelum && output_now <= output_mulai) {
                    Intent intent = new Intent(mContext, AngkatanActivity.class);
                    intent.putExtra("ID", data.getId_kelas());
                    intent.putExtra("NAMA", data.getNama_kelas());
                    intent.putExtra("MULAI", data.getTgl_mulai());
                    intent.putExtra("SELESAI", data.getTgl_selesai());
                    intent.putExtra("UJIAN", data.getTgl_ujian());
                    mContext.startActivity(intent);
                }else {
                    Snackbar.make(view, "Pendaftaran Diklat Teknis dibuka 7 hari sebelum Diklat dimulai",
                            Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mListData != null) ? mListData.size() : 0;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView tvKelas, tvKuota;
        public DataObjectHolder(View itemView) {
            super(itemView);
            tvKelas = itemView.findViewById(R.id.textView1);
            tvKuota = itemView.findViewById(R.id.textView2);
        }
    }
}
