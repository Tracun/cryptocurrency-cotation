package com.wb.tracun.braziliextest.Models;

import com.wb.tracun.braziliextest.R;
import com.wb.tracun.braziliextest.Services.ManageApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tracun on 09/01/2018.
 */

public class ListTickersBraziliex {

    public List<TickerBraziliex> list;

    public TickerBraziliex btc_brl;
    public TickerBraziliex eth_brl;
    public TickerBraziliex xmr_brl;
    public TickerBraziliex dash_brl;
    public TickerBraziliex ltc_brl;
    public TickerBraziliex bch_brl;
    public TickerBraziliex zec_brl;
    public TickerBraziliex crw_brl;
    public TickerBraziliex btg_brl;
    public TickerBraziliex mxt_brl;
    public TickerBraziliex sngls_brl;

    public ListTickersBraziliex(){

    }

    public void createList(){

        addLogoBraziliex();
        addNameBraziliex();

        list = new ArrayList<>();
        list.add(btc_brl);
        list.add(eth_brl);
        list.add(xmr_brl);
        list.add(dash_brl);
        list.add(ltc_brl);
        list.add(bch_brl);
        list.add(zec_brl);
        list.add(crw_brl);
        list.add(btg_brl);
        list.add(mxt_brl);
        list.add(sngls_brl);
    }

    void addLogoBraziliex(){
        btc_brl.setLogo(R.drawable.ic_btc_color);
        eth_brl.setLogo(R.drawable.ic_eth_color);
        xmr_brl.setLogo(R.drawable.ic_xmr_color);
        dash_brl.setLogo(R.drawable.ic_dash_color);
        ltc_brl.setLogo(R.drawable.ic_ltc_color);
        bch_brl.setLogo(R.drawable.ic_bch_color);
        zec_brl.setLogo(R.drawable.ic_zec_color);
//        crw_brl.setLogo(R.drawable.ic_coins_stacks_money_icon_icons_com_56191);
        btg_brl.setLogo(R.drawable.ic_btg_color);
//        mxt_brl.setLogo(R.drawable.ic_coins_stacks_money_icon_icons_com_56191);
        sngls_brl.setLogo(R.drawable.ic_sngls_color);
    }

    void addNameBraziliex(){
        btc_brl.setName("BTC");
        eth_brl.setName("ETH");
        xmr_brl.setName("XMR");
        dash_brl.setName("DASH");
        ltc_brl.setName("LTC");
        bch_brl.setName("BCH");
        zec_brl.setName("XEC");
        crw_brl.setName("CRW");
        btg_brl.setName("BTG");
        mxt_brl.setName("MXT");
        sngls_brl.setName("SNGLS");
    }

    public void toDollar(){

        float dolar = 1;

        for (TickerBraziliex ticker : list) {
            try {
                dolar = ManageApi.getDolar();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ticker.setLastDollar(ticker.getLast() / dolar);
        }
    }
}
