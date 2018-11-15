package com.alot.elearning;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.effect.EffectUpdateListener;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

public class SoalViewAdapter extends RecyclerView.Adapter<SoalViewAdapter.DataObjectHolder>{
    Context mContext;
    private List<Ujian> mListData;
    View view;

    public SoalViewAdapter(Context mContext, List<Ujian> mListData) {
        this.mContext = mContext;
        this.mListData = mListData;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_soal, parent, false);
        return new SoalViewAdapter.DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {
        final Ujian data = mListData.get(position);
        holder.tvSoal.setText(data.getNomor_soal()+". "+data.getIsi_soal());
        holder.rbA.setText("a. "+data.getA());
        holder.rbB.setText("b. "+data.getB());
        holder.rbC.setText("c. "+data.getC());
        holder.rbD.setText("d. "+data.getD());
        holder.rbE.setText("e. "+data.getE());

//        Log.e("boo", ""+data.getId_kelas());

        if (data.getId_kelas().substring(0,1).equals("a")){
//            holder.rbA.setChecked(true);
//            holder.rbB.setChecked(false);
//            holder.rbC.setChecked(false);
//            holder.rbD.setChecked(false);
//            holder.rbE.setChecked(false);

            holder.img_a.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_black_18dp));
            holder.img_b.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_c.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_d.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_e.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
        }else if (data.getId_kelas().substring(0,1).equals("b")) {
//            holder.rbA.setChecked(false);
//            holder.rbB.setChecked(true);
//            holder.rbC.setChecked(false);
//            holder.rbD.setChecked(false);
//            holder.rbE.setChecked(false);

            holder.img_a.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_b.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_black_18dp));
            holder.img_c.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_d.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_e.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
        }else if (data.getId_kelas().substring(0,1).equals("c")) {
//            holder.rbA.setChecked(false);
//            holder.rbB.setChecked(false);
//            holder.rbC.setChecked(true);
//            holder.rbD.setChecked(false);
//            holder.rbE.setChecked(false);

            holder.img_a.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_b.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_c.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_black_18dp));
            holder.img_d.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_e.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
        }else if (data.getId_kelas().substring(0,1).equals("d")) {
//            holder.rbA.setChecked(false);
//            holder.rbB.setChecked(false);
//            holder.rbC.setChecked(false);
//            holder.rbD.setChecked(true);
//            holder.rbE.setChecked(false);

            holder.img_a.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_b.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_c.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_d.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_black_18dp));
            holder.img_e.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
        }else if (data.getId_kelas().substring(0,1).equals("e")) {
//            holder.rbA.setChecked(false);
//            holder.rbB.setChecked(false);
//            holder.rbC.setChecked(false);
//            holder.rbD.setChecked(false);
//            holder.rbE.setChecked(true);

            holder.img_a.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_b.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_c.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_d.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_e.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_black_18dp));
        }else {
            holder.img_a.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_b.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_c.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_d.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            holder.img_e.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
        }

        if (data.getId_kelas().substring(1,2).equals("1")){
            holder.tvSoal.setEnabled(false);
            holder.rbA.setEnabled(false);
            holder.rbB.setEnabled(false);
            holder.rbC.setEnabled(false);
            holder.rbD.setEnabled(false);
            holder.rbE.setEnabled(false);
        }else {
            holder.tvSoal.setEnabled(true);
            holder.rbA.setEnabled(true);
            holder.rbB.setEnabled(true);
            holder.rbC.setEnabled(true);
            holder.rbD.setEnabled(true);
            holder.rbE.setEnabled(true);
        }

        holder.ll_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionChose(data.getId_soal(), "a");
                holder.img_a.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_black_18dp));
                holder.img_b.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
                holder.img_c.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
                holder.img_d.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
                holder.img_e.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            }
        });

        holder.ll_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionChose(data.getId_soal(), "b");
                holder.img_a.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
                holder.img_b.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_black_18dp));
                holder.img_c.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
                holder.img_d.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
                holder.img_e.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            }
        });

        holder.ll_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionChose(data.getId_soal(), "c");
                holder.img_a.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
                holder.img_b.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
                holder.img_c.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_black_18dp));
                holder.img_d.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
                holder.img_e.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            }
        });

        holder.ll_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionChose(data.getId_soal(), "d");
                holder.img_a.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
                holder.img_b.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
                holder.img_c.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
                holder.img_d.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_black_18dp));
                holder.img_e.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
            }
        });

        holder.ll_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionChose(data.getId_soal(), "e");
                holder.img_a.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
                holder.img_b.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
                holder.img_c.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
                holder.img_d.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_outline_blank_black_18dp));
                holder.img_e.setImageDrawable(mContext.getResources().getDrawable(R.drawable.baseline_check_box_black_18dp));
            }
        });

//        holder.rbA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                holder.rbA.setChecked(true);
////                holder.rbB.setChecked(false);
////                holder.rbC.setChecked(false);
////                holder.rbD.setChecked(false);
////                holder.rbE.setChecked(false);
//                if (!data.getId_kelas().substring(0,1).equals("a")) {
//                    if (isChecked) {
//                        ActionChose(data.getId_soal(), "a");
//                    }
//                }
//            }
//        });
//
//        holder.rbB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                holder.rbA.setChecked(false);
////                holder.rbB.setChecked(true);
////                holder.rbC.setChecked(false);
////                holder.rbD.setChecked(false);
////                holder.rbE.setChecked(false);
//                if (!data.getId_kelas().substring(0,1).equals("b")) {
//                    if (isChecked) {
//                        ActionChose(data.getId_soal(), "b");
//                    }
//                }
//            }
//        });
//
//        holder.rbC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                holder.rbA.setChecked(false);
////                holder.rbB.setChecked(false);
////                holder.rbC.setChecked(true);
////                holder.rbD.setChecked(false);
////                holder.rbE.setChecked(false);
//                if (!data.getId_kelas().substring(0,1).equals("c")) {
//                    if (isChecked) {
//                        ActionChose(data.getId_soal(), "c");
//                    }
//                }
//            }
//        });
//
//        holder.rbD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                holder.rbA.setChecked(false);
////                holder.rbB.setChecked(false);
////                holder.rbC.setChecked(false);
////                holder.rbD.setChecked(true);
////                holder.rbE.setChecked(false);
//                if (!data.getId_kelas().substring(0,1).equals("d")) {
//                    if (isChecked) {
//                        ActionChose(data.getId_soal(), "d");
//                    }
//                }
//            }
//        });
//
//        holder.rbE.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                holder.rbA.setChecked(false);
////                holder.rbB.setChecked(false);
////                holder.rbC.setChecked(false);
////                holder.rbD.setChecked(false);
////                holder.rbE.setChecked(true);
//                if (!data.getId_kelas().substring(0,1).equals("e")) {
//                    if (isChecked) {
//                        ActionChose(data.getId_soal(), "e");
//                    }
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return (mListData != null) ? mListData.size() : 0;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView tvSoal;
        TextView rbA, rbB, rbC, rbD, rbE;
        ImageView img_a, img_b, img_c, img_d, img_e;
        LinearLayout ll_a, ll_b, ll_c, ll_d, ll_e;

        public DataObjectHolder(View itemView) {
            super(itemView);
            tvSoal = itemView.findViewById(R.id.tv_question);
            rbA = itemView.findViewById(R.id.rb_answer_a);
            rbB = itemView.findViewById(R.id.rb_answer_b);
            rbC = itemView.findViewById(R.id.rb_answer_c);
            rbD = itemView.findViewById(R.id.rb_answer_d);
            rbE = itemView.findViewById(R.id.rb_answer_e);

            img_a = itemView.findViewById(R.id.img_a);
            img_b = itemView.findViewById(R.id.img_b);
            img_c = itemView.findViewById(R.id.img_c);
            img_d = itemView.findViewById(R.id.img_d);
            img_e = itemView.findViewById(R.id.img_e);

            ll_a = itemView.findViewById(R.id.ll_a);
            ll_b = itemView.findViewById(R.id.ll_b);
            ll_c = itemView.findViewById(R.id.ll_c);
            ll_d = itemView.findViewById(R.id.ll_d);
            ll_e = itemView.findViewById(R.id.ll_e);


//            user = Utilities.getUser(mContext);
//            if (!user.getId_angkatan().isEmpty()){
//                cbSelect.setVisibility(View.GONE);
//            }

//            boolean flag=false;
//            for (int a=0; a<mListData.size(); a++){
////                Log.e("flag", ""+mListData.get(a).getId_kelas().substring(1,2));
//                if (mListData.get(a).getId_kelas().substring(1,2).equals("1")){
//                    flag = true;
//                }
//            }
//
//            if (flag){
//                rbA.setEnabled(false);
//                rbB.setEnabled(false);
//                rbC.setEnabled(false);
//                rbD.setEnabled(false);
//                rbE.setEnabled(false);
//            }else {
//                rbA.setEnabled(true);
//                rbB.setEnabled(true);
//                rbC.setEnabled(true);
//                rbD.setEnabled(true);
//                rbE.setEnabled(true);
//            }
        }
    }

    private void ActionChose(String id, String jawaban){
        Log.e("action", id+" "+jawaban);
        DataHelper dbHelper = new DataHelper(mContext);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_jawaban WHERE id_soal = '"+id+"'", null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            db = dbHelper.getReadableDatabase();
            dbHelper.onInsertJawaban(db, id, jawaban);
            Intent mIntent = new Intent("ALERT_STATUS_CHANGE");
            mIntent.putExtra("IDSOAL", id);
            mIntent.putExtra("JAWABAN", jawaban);
            mContext.sendBroadcast(mIntent);
//            notifyDataSetChanged();
        }else {
            db = dbHelper.getReadableDatabase();
            dbHelper.onUpdateJawaban(db, id, jawaban);
            Intent mIntent = new Intent("ALERT_STATUS_CHANGE");
            mIntent.putExtra("IDSOAL", id);
            mIntent.putExtra("JAWABAN", jawaban);
            mContext.sendBroadcast(mIntent);
//            notifyDataSetChanged();
        }
    }
}
