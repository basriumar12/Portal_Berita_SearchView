package com.blogbasbas.beritabola.network;

import retrofit2.Retrofit;
import com.blogbasbas.beritabola.network.Constant;
import com.blogbasbas.beritabola.network.ServiceRetrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 15/07/2018.
 */

public class InitRetrofit {

    // base url isi ip komputer sendiri

    public static String BASE_URL ="http://192.168.43.127/berita/";

    public static Retrofit setInit (){
        return  new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServiceRetrofit getInstance (){
        return setInit().create(ServiceRetrofit.class);
    }

}
