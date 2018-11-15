package com.alot.elearning;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class MateriViewAdapter extends RecyclerView.Adapter<MateriViewAdapter.DataObjectHolder>{
    Context mContext;
    private List<Materi> mListData;
    View view;
    User user;

    public MateriViewAdapter(Context mContext, List<Materi> mListData) {
        this.mContext = mContext;
        this.mListData = mListData;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_materi, parent, false);
        return new MateriViewAdapter.DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {
        final Materi data = mListData.get(position);
        holder.tvTitle.setText(data.getNama_materi());
        holder.tvDeskrip.setText(data.getJumlah_file()+" file");

        if(data.getCheck().equals("1")) {
//            holder.cbSelect.setChecked(true);
            holder.button.setVisibility(View.GONE);
        }else {
//            holder.cbSelect.setChecked(false);
            holder.button.setVisibility(View.VISIBLE);
        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utilities.isNetworkAvailable(mContext)) {
                    Intent mIntent = new Intent("ALERT_STATUS_CHANGE");
                    mIntent.putExtra("ID", data.getId_materi());
                    mIntent.putExtra("NAMA", data.getNama_materi());
                    mContext.sendBroadcast(mIntent);
                    notifyDataSetChanged();
                } else {
                    Snackbar.make(view, "Tidak ada koneksi internet",
                            Snackbar.LENGTH_LONG).show();
                }
            }
        });

//        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked) {
//                    data.setCheck("1");
//                    holder.cbSelect.setChecked(true);
//                }else {
//                    data.setCheck("0");
//                    holder.cbSelect.setChecked(false);
//                }
//                Intent mIntent = new Intent("ALERT_STATUS_CHANGE");
//                mIntent.putExtra("ID", data.getId_materi());
//                mIntent.putExtra("CHECK", data.getCheck());
//                mContext.sendBroadcast(mIntent);
//                notifyDataSetChanged();
//            }
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (user.getId_angkatan().isEmpty()) {
//                    if (!holder.cbSelect.isChecked()) {
//                        data.setCheck("1");
//                        holder.cbSelect.setChecked(true);
//                    } else {
//                        data.setCheck("0");
//                        holder.cbSelect.setChecked(false);
//                    }
//                    Intent mIntent = new Intent("ALERT_STATUS_CHANGE");
//                    mIntent.putExtra("ID", data.getId_materi());
//                    mIntent.putExtra("CHECK", data.getCheck());
//                    mContext.sendBroadcast(mIntent);
//                    notifyDataSetChanged();
//                mContext.startActivity(new Intent(mContext, MateriViewerActivity.class));
//                }else {
//                    Intent intent = new Intent(mContext, MateriViewerActivity.class);
//                    intent.putExtra("ID", data.getId_materi());
//                    mContext.startActivity(intent);
//                }

                if (data.getCheck().equals("1")) {
                    if (user.getPretest().equals("1")) {
                        Intent intent = new Intent(mContext, MateriViewerActivity.class);
                        intent.putExtra("ID", data.getId_materi());
                        mContext.startActivity(intent);
                    }else {
                        Snackbar.make(view, "Silahkan kerjakan ujian pretest terlebih dahulu",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(view, "Silahkan download materi terlebih dahulu",
                            Snackbar.LENGTH_LONG).show();
                }
            }
        });

//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Intent intent = new Intent(mContext, MateriViewerActivity.class);
//                intent.putExtra("ID", data.getId_materi());
//                mContext.startActivity(intent);
//                return false;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return (mListData != null) ? mListData.size() : 0;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDeskrip;
        CheckBox cbSelect;
        Button button;

        public DataObjectHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.textView1);
            tvDeskrip = itemView.findViewById(R.id.textView2);
            cbSelect = itemView.findViewById(R.id.checkbox);
            button = itemView.findViewById(R.id.btnDonlod);

            user = Utilities.getUser(mContext);
//            if (!user.getId_angkatan().isEmpty()){
//                cbSelect.setVisibility(View.GONE);
//            }
        }
    }
}
