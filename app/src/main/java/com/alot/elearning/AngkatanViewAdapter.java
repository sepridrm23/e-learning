package com.alot.elearning;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AngkatanViewAdapter extends RecyclerView.Adapter<AngkatanViewAdapter.DataObjectHolder>{
    Context mContext;
    private List<Angkatan> mListData;
    private List<User> mListUser;
    View view;

    public AngkatanViewAdapter(Context mContext, List<Angkatan> mListData, List<User> mListUser) {
        this.mContext = mContext;
        this.mListData = mListData;
        this.mListUser = mListUser;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_class, parent, false);
        return new AngkatanViewAdapter.DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        final Angkatan data = mListData.get(position);
        holder.tvKelas.setText(data.getNama_angkatan());
//        holder.tvKuota.setText(data.getJumlah_kuota_terisa()+" orang tersisa dari "+data.getJumlah_kuota()+" tersedia");
        holder.tvKuota.setText("Dibuka untuk "+data.getJumlah_kuota()+" orang \nTerisi "+data.getJumlah_kuota_terisa()+" orang");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, MateriActivity.class);
//                intent.putExtra("ID", data.getId_kelas());
//                mContext.startActivity(intent);

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(true)
                        .setTitle("Konfirmasi")
                        .setMessage("Tidak dapat mengganti pelatihan sebelum pelatihan selesai atau drop out. Lanjutkan ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                User user = Utilities.getUser(mContext);
                                if (!user.getId_angkatan().isEmpty()){
                                    Snackbar.make(view, "Anda sudah terdaftar pada pelatihan "+user.getNama_kelas()+" ("+user.getNama_angkatan()+")",
                                            Snackbar.LENGTH_LONG).show();
                                }else {
//                                    int sisa = Integer.parseInt(data.getJumlah_kuota_terisa()) - 1;
                                    if (!data.getJumlah_kuota().equals(data.getJumlah_kuota_terisa())) {
                                        SetAngkatan(user.getId_user(), data.getId_angkatan(), data.getNama_angkatan());
                                    }else {
                                        Snackbar.make(view, "Pelatihan yang diikuti telah terisi penuh",
                                                Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

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

    public void SetAngkatan(String iduser, final String idangkatan, final String namaangkatan) {
        final ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLPhp())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        final APIServices apiService = retrofit.create(APIServices.class);
        Call<SetValue> call = apiService.setangkatan(random, iduser, idangkatan);
        call.enqueue(new Callback<SetValue>() {
            @Override
            public void onResponse(@NonNull Call<SetValue> call, @NonNull Response<SetValue> response) {
                dialog.dismiss();
                if (response.body() != null) {
                    int success = response.body().getSuccess();
                    if (success == 1) {
                        User user = Utilities.getUser(mContext);
                        List<User> temp_user = new ArrayList<>();
                        temp_user.add(new User(
                                user.getId_user(), user.getUsername(), user.getNip(), user.getNama(), user.getEmail(), user.getNo_hp(),
                                user.getNo_hp_other(), user.getNo_wa(), user.getNo_rekening(), user.getNama_bank(), user.getProvinsi(),
                                user.getKabupaten(), mListUser.get(0).getId_kelas(), mListUser.get(0).getNama_kelas(),
                                idangkatan, namaangkatan, mListUser.get(0).getTgl_mulai(), mListUser.get(0).getTgl_selesai(), mListUser.get(0).getTgl_ujian(),
                                "0"
                        ));
                        for (User add : temp_user) {
                            Utilities.setUser(mContext, add);
                        }

                        Snackbar.make(view, "Diklat Teknis berhasil diikuti",
                                Snackbar.LENGTH_LONG).show();

                        int total = Integer.parseInt(response.body().getMessage())+1;
                        Intent mIntent = new Intent("ALERT_STATUS_CHANGE");
                        mIntent.putExtra("ID", idangkatan);
                        mIntent.putExtra("JUMLAH", String.valueOf(total));
                        mContext.sendBroadcast(mIntent);
                        notifyDataSetChanged();
                    }else if (success == 2) {
                        Snackbar.make(view, "Diklat Teknis yang diikuti telah terisi penuh",
                                Snackbar.LENGTH_LONG).show();

                        Intent mIntent = new Intent("ALERT_STATUS_CHANGE");
                        mIntent.putExtra("ID", idangkatan);
                        mIntent.putExtra("JUMLAH", response.body().getMessage());
                        mContext.sendBroadcast(mIntent);
                        notifyDataSetChanged();
                    }else{
                        Snackbar.make(view, "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(view, "Tidak terhubung ke server",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SetValue> call, Throwable t) {
                dialog.dismiss();
                Snackbar.make(view, "Tidak terhubung ke server",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

}
