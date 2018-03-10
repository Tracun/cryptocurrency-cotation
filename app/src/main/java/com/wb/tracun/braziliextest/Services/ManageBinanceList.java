package com.wb.tracun.braziliextest.Services;

import android.util.Log;

import com.wb.tracun.braziliextest.Models.TickerBinance;
import com.wb.tracun.braziliextest.R;

import java.io.IOException;
import java.util.List;

/**
 * Created by Tracun on 16/02/2018.
 */

public class ManageBinanceList {

    private static final int BTC_USDT = 0;
    private static float lastPriceDolar = 0;

    public static void toDollar(List<TickerBinance> list){
        for (int i = 0; i < 3; i++){
            list.get(i).setLastPriceDollar(Float.parseFloat(list.get(i).getLastPrice()));
        }
        for (int i = 3; i < list.size(); i++) {

            list.get(i).setLastPriceDollar(Float.parseFloat(list.get(i).getLastPrice()) * Float.parseFloat(list.get(BTC_USDT).getLastPrice()));
//            Log.i("Dolar", "Name: " + list.get(i).getSymbol() + " - Preco dolar: " + list.get(i).getLastPriceDollar());
        }
    }

    public static void toReal(List<TickerBinance> list) throws IOException {

        if(ManageApi.getDolar() != 0){
            lastPriceDolar = ManageApi.getDolar();
        }

        for (int i = 0; i < list.size(); i++) {

            list.get(i).setLastPriceReal(list.get(i).getLastPriceDollar() * lastPriceDolar);
//            Log.i("Dolar", "Name: " + list.get(i).getSymbol() + " - Preco real: " + list.get(i).getLastPriceReal());
        }
    }

    public static void addIcon(List<TickerBinance> list){
        list.get(0).setLogo(R.drawable.ic_btc_color);
        list.get(1).setLogo(R.drawable.ic_eth_color);
        list.get(2).setLogo(R.drawable.ic_ltc_color);
        list.get(3).setLogo(R.drawable.ic_eth_color);
        list.get(4).setLogo(R.drawable.ic_sngls_color);
        list.get(5).setLogo(R.drawable.ic_ltc_color);
        list.get(6).setLogo(R.drawable.ic_iota_color);
        list.get(7).setLogo(R.drawable.ic_dash_color);
        list.get(8).setLogo(R.drawable.ic_btg_color);
        list.get(9).setLogo(R.drawable.ic_xrp_color);
        list.get(10).setLogo(R.drawable.ic_xmr_color);
    }
}
