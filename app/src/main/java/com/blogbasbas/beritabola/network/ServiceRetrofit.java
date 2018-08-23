package com.blogbasbas.beritabola.network;

import com.blogbasbas.beritabola.model.ResponseBerita;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by User on 15/07/2018.
 */

public interface ServiceRetrofit {

    //get data berita
    @GET("tampil_berita.php")
    Call<ResponseBerita> getData() ;





}
