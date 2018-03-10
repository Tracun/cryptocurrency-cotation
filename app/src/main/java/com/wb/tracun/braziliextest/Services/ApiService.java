package com.wb.tracun.braziliextest.Services;

import com.wb.tracun.braziliextest.Models.Asks;
import com.wb.tracun.braziliextest.Models.Bids;
import com.wb.tracun.braziliextest.Models.DolarValue;
import com.wb.tracun.braziliextest.Models.ListTickersBraziliex;
import com.wb.tracun.braziliextest.Models.ResponsePrivateAPI;
import com.wb.tracun.braziliextest.Models.TickerBinance;
import com.wb.tracun.braziliextest.Models.TickerBraziliex;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Tracun on 08/01/2018.
 */

public interface ApiService {

    /*
     * Braziliex
     */
    @GET("ticker/{coinID}")
    Call<TickerBraziliex> getTicker(@Path("coinID") String coinID);

    @GET("ticker")
    Call<ListTickersBraziliex> getAllTicker();

    @GET("{coinID}")
    Call<Asks> getAskOrder(@Path("coinID") String coinID);

    @GET("{coinID}")
    Call<Bids> getBidOrder(@Path("coinID") String coinID);

    @POST("private/")
    Call<ResponsePrivateAPI> balance(@Header("Key") String key, @Header("Sign") String sign);

    /*
     * Binance
     */
    @GET("api/v1/ticker/24hr")
    Call<List<TickerBinance>> getAllTickerBinance();

    @GET("api/v1/ticker/24hr")
    Call<TickerBinance> getAllPrices();

    @GET("api/v1/ticker/24hr")
    Call<TickerBinance> getTickerBinance(@Query("symbol") String symbol);

    /*
     * Dollar
     */
    @GET("/cotacao/v1/valores?moedas=USD&alt=json")
    Call<DolarValue> getDollarValue();
}
