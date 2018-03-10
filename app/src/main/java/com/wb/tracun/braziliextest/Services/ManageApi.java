package com.wb.tracun.braziliextest.Services;

import android.util.Log;

import com.wb.tracun.braziliextest.Models.Asks;
import com.wb.tracun.braziliextest.Models.Bids;
import com.wb.tracun.braziliextest.Models.DolarValue;
import com.wb.tracun.braziliextest.Models.ListTickersBraziliex;
import com.wb.tracun.braziliextest.Models.TickerBinance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tracun on 08/01/2018.
 */

public class ManageApi {

    public static final String ROOT_URL_PUBLIC = "https://braziliex.com/api/v1/public/";
    public static final String ROOT_URL_PUBLIC_BINANCE = "https://api.binance.com/";
    public static final String ROOT_URL_PUBLIC_DOLAR = "http://api.promasters.net.br/";
    public static final String ROOT_URL_PUBLIC_ORDER = "https://braziliex.com/api/v1/public/orderbook/";
    public static final String ROOT_URL_PRIVATE = "https://braziliex.com/api/v1/private/";

    public ListTickersBraziliex mListTickersBraziliex;
    public List<TickerBinance> mListTickersBinance = new ArrayList<>();
    public TickerBinance tickerBinance;

    public String[] getTicker(){

        ListTickersBraziliex listTickersBraziliex = null;
        try {
            listTickersBraziliex = getAllTickerBraziliex();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(listTickersBraziliex != null){
            String btc_brl = "BTC\t\tR$: " + listTickersBraziliex.btc_brl.getLast() + "\t\t" + listTickersBraziliex.btc_brl.getPercentChange() + "%";
            String eth_brl = "ETH\t\tR$: " + listTickersBraziliex.eth_brl.getLast() + "\t\t" + listTickersBraziliex.eth_brl.getPercentChange() + "%";
            String xmr_brl = "XMR\t\tR$: " + listTickersBraziliex.xmr_brl.getLast() + "\t\t" + listTickersBraziliex.xmr_brl.getPercentChange() + "%";
            String dash_brl = "DASH\t\tR$: " + listTickersBraziliex.dash_brl.getLast() + "\t\t" + listTickersBraziliex.dash_brl.getPercentChange() + "%";
            String ltc_brl = "LTC\t\tR$: " + listTickersBraziliex.ltc_brl.getLast() + "\t\t" + listTickersBraziliex.ltc_brl.getPercentChange() + "%";
            String bch_brl = "BCH\t\tR$: " + listTickersBraziliex.bch_brl.getLast() + "\t\t" + listTickersBraziliex.bch_brl.getPercentChange() + "%";
            String zec_brl = "ZEC\t\tR$: " + listTickersBraziliex.zec_brl.getLast() + "\t\t" + listTickersBraziliex.zec_brl.getPercentChange() + "%";
            String crw_brl = "CRW\t\tR$: " + listTickersBraziliex.crw_brl.getLast() + "\t\t" + listTickersBraziliex.crw_brl.getPercentChange() + "%";
            String btg_brl = "BTG\t\tR$: " + listTickersBraziliex.btg_brl.getLast() + "\t\t" + listTickersBraziliex.btg_brl.getPercentChange() + "%";
            String mxt_brl = "MXT\t\tR$: " + listTickersBraziliex.mxt_brl.getLast() + "\t\t" + listTickersBraziliex.mxt_brl.getPercentChange() + "%";
            String sngls_brl = "SNGLS\t\tR$: " + listTickersBraziliex.sngls_brl.getLast() + "\t\t" + listTickersBraziliex.sngls_brl.getPercentChange() + "%";

            return new String[]{btc_brl, eth_brl, sngls_brl, dash_brl, ltc_brl, bch_brl, zec_brl, crw_brl, btg_brl, mxt_brl, xmr_brl};
        }

        return null;
    }

    public ListTickersBraziliex getAllTickerBraziliex() throws IOException{
        // Create a very simple REST adapter which points the ApiService API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL_PUBLIC)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our ApiService API interface.
        ApiService apiService = retrofit.create(ApiService.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<ListTickersBraziliex> call = apiService.getAllTicker();

        mListTickersBraziliex = call.execute().body();

        return mListTickersBraziliex;
    }

    public List<TickerBinance> getAllTickerBinace() throws IOException{
        // Create a very simple REST adapter which points the ApiService API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL_PUBLIC_BINANCE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our ApiService API interface.
        ApiService apiService = retrofit.create(ApiService.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<List<TickerBinance>> call = apiService.getAllTickerBinance();

        mListTickersBinance = call.execute().body();

//        Log.i("Binance", "isEmpty: " + mListTickersBinance);
//        Log.i("Binance", "Last BTC_USD: " + mListTickersBinance.get(0).getLastPrice());
//        Log.i("Binance", "Last SNGLS_BTC: " + mListTickersBinance.get(0).getLastPrice());

        return mListTickersBinance;
    }

    public List<TickerBinance> getPreferredTickerBinance(List<String> preferredTickers) throws IOException{
        // Create a very simple REST adapter which points the ApiService API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL_PUBLIC_BINANCE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our ApiService API interface.
        ApiService apiService = retrofit.create(ApiService.class);
        for (String symbol: preferredTickers) {
            final Call<TickerBinance> call = apiService.getTickerBinance(symbol);

            tickerBinance = new TickerBinance();
            tickerBinance = call.execute().body();

            if(tickerBinance != null){
                mListTickersBinance.add(tickerBinance);
            }

            Log.i("Lucassss", "isNull list: " + mListTickersBinance.size());
        }

        return mListTickersBinance;
    }

    public Asks getAskOrderBraziliex(String coinID) throws IOException{

        // Create a very simple REST adapter which points the ApiService API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL_PUBLIC_ORDER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our ApiService API interface.
        ApiService apiService = retrofit.create(ApiService.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<Asks> call = apiService.getAskOrder(coinID);

        //Return an object with contais a list of asks orders
        return call.execute().body();

    }

    public Bids getBidOrderBraziliex(String coinID) throws IOException{

        // Create a very simple REST adapter which points the ApiService API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL_PUBLIC_ORDER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our ApiService API interface.
        ApiService apiService = retrofit.create(ApiService.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<Bids> call = apiService.getBidOrder(coinID);

        //Return an object with contais a list of bids orders
        return call.execute().body();
    }

    public static float getDolar() throws IOException {

        // Create a very simple REST adapter which points the ApiService API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL_PUBLIC_DOLAR)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our ApiService API interface.
        ApiService apiService = retrofit.create(ApiService.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<DolarValue> call = apiService.getDollarValue();

        DolarValue dolarValue = call.execute().body();

        Log.i("DOLARRR", "LastPrice: USD " + dolarValue.getValores().getUsd().getValor());

        return Float.parseFloat(dolarValue.getValores().getUsd().getValor());

    }

}
