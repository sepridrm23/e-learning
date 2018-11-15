package com.alot.elearning;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class HasilViewAdapter extends RecyclerView.Adapter<HasilViewAdapter.DataObjectHolder>{
    Context mContext;
    private List<Hasil> mListData;
    User user;

    public HasilViewAdapter(Context mContext, List<Hasil> mListData) {
        this.mContext = mContext;
        this.mListData = mListData;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_hasil, parent, false);
        return new HasilViewAdapter.DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        final Hasil data = mListData.get(position);

        if (data.getId_user().equals(user.getId_user())){
            holder.linearLayout.setBackgroundColor(Color.parseColor("#62abf4"));
        }else {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        holder.tvNo.setText(data.getNo());
        holder.tvNama.setText(data.getNama());
        holder.tvAsal.setText(data.getKabupaten());

//        if (data.getNilai() == null){
//            holder.tvNilai.setText("-");
//        }else {
//            holder.tvNilai.setText(data.getNilai());
//        }

        if (data.getNilai_pos() == null) {
            holder.tvNilaiPos.setText("-");
        }else {
            holder.tvNilaiPos.setText(data.getNilai_pos());
        }

        if (data.getNilai_pre() == null) {
            holder.tvNilaiPre.setText("-");
        }else {
            holder.tvNilaiPre.setText(data.getNilai_pre());
        }
    }

    @Override
    public int getItemCount() {
        return (mListData != null) ? mListData.size() : 0;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView tvNo, tvNama, tvAsal, tvNilai, tvNilaiPre, tvNilaiPos;
        LinearLayout linearLayout;
        public DataObjectHolder(View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvNo = itemView.findViewById(R.id.tvNo);
            tvAsal = itemView.findViewById(R.id.tvAsal);
            tvNilai = itemView.findViewById(R.id.tvNilai);
            tvNilaiPos = itemView.findViewById(R.id.tvNilaiPos);
            tvNilaiPre = itemView.findViewById(R.id.tvNilaiPre);
            linearLayout = itemView.findViewById(R.id.line1);

            user = Utilities.getUser(mContext);
        }
    }
}
