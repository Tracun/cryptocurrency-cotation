package com.wb.tracun.braziliextest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by u4239 on 08/01/2018.
 */

public interface ApiService {

    @GET("ticker/{coinID}")
    Call<TickerProperties> getTicker(@Path("coinID") String coinID);

    @GET("ticker")
    Call<Currency> getAllTicker();

    @POST("{key}")
    Call<Void> buy(@Path("key") double key, @Path("nonce") int nonce);
}
