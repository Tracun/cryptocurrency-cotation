package com.wb.tracun.braziliextest;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by u4239 on 08/01/2018.
 */

public class ManageApi {

    public static final String ROOT_URL_PUBLIC = "https://braziliex.com/api/v1/public/";
    public static final String ROOT_URL_PRIVATE = "https://braziliex.com/api/v1/private/";

    Currency mCurrency;

    public ManageApi(){

    }

    public String[] getTicker(){

        Currency currency = null;
        try {
            currency = getAllTicker();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(currency != null){
            String btc_brl = "BTC\t\tR$: " + currency.btc_brl.getLast() + "\t\t" + currency.btc_brl.getPercentChange() + "%";
            String eth_brl = "ETH\t\tR$: " + currency.eth_brl.getLast() + "\t\t" + currency.eth_brl.getPercentChange() + "%";
            String xmr_brl = "XMR\t\tR$: " + currency.xmr_brl.getLast() + "\t\t" + currency.xmr_brl.getPercentChange() + "%";
            String dash_brl = "DASH\t\tR$: " + currency.dash_brl.getLast() + "\t\t" + currency.dash_brl.getPercentChange() + "%";
            String ltc_brl = "LTC\t\tR$: " + currency.ltc_brl.getLast() + "\t\t" + currency.ltc_brl.getPercentChange() + "%";
            String bch_brl = "BCH\t\tR$: " + currency.bch_brl.getLast() + "\t\t" + currency.bch_brl.getPercentChange() + "%";
            String zec_brl = "ZEC\t\tR$: " + currency.zec_brl.getLast() + "\t\t" + currency.zec_brl.getPercentChange() + "%";
            String crw_brl = "CRW\t\tR$: " + currency.crw_brl.getLast() + "\t\t" + currency.crw_brl.getPercentChange() + "%";
            String btg_brl = "BTG\t\tR$: " + currency.btg_brl.getLast() + "\t\t" + currency.btg_brl.getPercentChange() + "%";
            String mxt_brl = "MXT\t\tR$: " + currency.mxt_brl.getLast() + "\t\t" + currency.mxt_brl.getPercentChange() + "%";
            String sngls_brl = "SNGLS\t\tR$: " + currency.sngls_brl.getLast() + "\t\t" + currency.sngls_brl.getPercentChange() + "%";

            return new String[]{btc_brl, eth_brl, sngls_brl, dash_brl, ltc_brl, bch_brl, zec_brl, crw_brl, btg_brl, mxt_brl, xmr_brl};
        }

        return null;
    }

    Currency getAllTicker() throws IOException{
        // Create a very simple REST adapter which points the ApiService API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL_PUBLIC)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our ApiService API interface.
        ApiService apiService = retrofit.create(ApiService.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<Currency> call = apiService.getAllTicker();

        mCurrency = call.execute().body();

        return mCurrency;
    }
}
