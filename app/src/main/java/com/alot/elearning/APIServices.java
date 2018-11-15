package com.alot.elearning;
import java.util.Set;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIServices {
    @FormUrlEncoded
    @POST("signin.php")
    Call<GetValue<User>> signin(@Field("xkey") String xkey,
                                @Field("username") String username,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("signout.php")
    Call<SetValue> signout(@Field("xkey") String xkey,
                                @Field("iduser") String id);

    @FormUrlEncoded
    @POST("checkid.php")
    Call<SetValue> checkid(@Field("xkey") String xkey,
                           @Field("iduser") String id);

    @FormUrlEncoded
    @POST("signup.php")
    Call<SetValue> signup(@Field("xkey") String xkey,
                          @Field("nip") String nip,
                          @Field("nama") String nama,
                          @Field("email") String email,
                          @Field("hptsel") String hptsel,
                          @Field("hplain") String hplain,
                          @Field("hpwa") String hpwa,
                          @Field("norek") String norek,
                          @Field("bank") String bank,
                          @Field("prov") String prov,
                          @Field("kab") String kab);

    @FormUrlEncoded
    @POST("updateuser.php")
    Call<SetValue> updateuser(@Field("xkey") String xkey,
                              @Field("iduser") String iduser,
                                @Field("hplain") String hplain,
                                @Field("norek") String norek,
                                @Field("bank") String bank);

    @FormUrlEncoded
    @POST("getclass.php")
    Call<GetValue<Class>> getclass(@Field("xkey") String xkey);

    @FormUrlEncoded
    @POST("getangkatan.php")
    Call<GetValue<Angkatan>> getangkatan(@Field("xkey") String xkey,
                                      @Field("idkelas") String idkelas);

    @FormUrlEncoded
    @POST("getmateri.php")
    Call<GetValue<Materi>> getmateri(@Field("xkey") String xkey,
                                         @Field("idkelas") String idkelas);

    @FormUrlEncoded
    @POST("getdetailmateri.php")
    Call<GetValue<MateriViewer>> getdetailmateri(@Field("xkey") String xkey,
                                     @Field("idmateri") String idmateri);

    @FormUrlEncoded
    @POST("setangkatan.php")
    Call<SetValue> setangkatan(@Field("xkey") String xkey,
                               @Field("iduser") String iduser,
                               @Field("idangkatan") String idangkatan);

    @FormUrlEncoded
    @POST("setjawaban.php")
    Call<SetValue> setjawaban(@Field("xkey") String xkey,
                               @Field("iduser") String iduser,
                               @Field("idsoal") String idsoal,
                              @Field("jawaban") String jawaban,
                              @Field("idangkatan") String idangkatan,
                              @Field("tgl") String tgl);

//    @FormUrlEncoded
//    @POST("setjawabanpretes.php")
//    Call<SetValue> setjawabanpretes(@Field("xkey") String xkey,
//                              @Field("iduser") String iduser,
//                              @Field("idsoal") String idsoal,
//                              @Field("jawaban") String jawaban,
//                              @Field("idangkatan") String idangkatan,
//                              @Field("tgl") String tgl);

    @FormUrlEncoded
    @POST("getujian.php")
    Call<GetValue<Ujian>> getujian(@Field("xkey") String xkey,
                                   @Field("iduser") String iduser,
                                 @Field("idkelas") String idkelas,
                                   @Field("idangkatan") String idangkatan,
                                   @Field("tglujian") String tglujian);

    @FormUrlEncoded
    @POST("sethasil.php")
    Call<SetValue> sethasil(@Field("xkey") String xkey,
                            @Field("iduser") String iduser,
                            @Field("id_angkatan") String id_angkatan,
                            @Field("tgl_ujian") String tgl_ujian,
                            @Field("id_kelas") String id_kelas,
                            @Field("flag") String flag);

//    @FormUrlEncoded
//    @POST("sethasilpretes.php")
//    Call<SetValue> sethasilpretes(@Field("xkey") String xkey,
//                            @Field("iduser") String iduser,
//                            @Field("id_angkatan") String id_angkatan,
//                            @Field("tgl_ujian") String tgl_ujian,
//                            @Field("id_kelas") String id_kelas);

    @FormUrlEncoded
    @POST("gethasil.php")
    Call<GetValue<Hasil>> gethasil(@Field("xkey") String xkey,
                                   @Field("iduser") String iduser);

    @FormUrlEncoded
    @POST("lupapass.php")
    Call<SetValue> lupapass(@Field("xkey") String xkey,
                            @Field("nip") String nip,
                            @Field("nama") String nama,
                            @Field("email") String email,
                            @Field("phone") String phone);
}