package com.wb.tracun.braziliextest.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.wb.tracun.braziliextest.MainActivity;
import com.wb.tracun.braziliextest.Models.ListTickersBraziliex;
import com.wb.tracun.braziliextest.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Tracun on 16/01/2018.
 */

public class Notification {

    private ListTickersBraziliex mListTickersBraziliex;
    private float mDefaultValue;
    private Context mContext;

    public Notification(ListTickersBraziliex listTickersBraziliex, float defaultValue, Context context){
        this.mListTickersBraziliex = listTickersBraziliex;
        this.mDefaultValue = defaultValue;
        this.mContext = context;
    }

    //Exibe notificacao para os valor iguais ou maiores que o definido
    public void moreThan(int position){

        Log.i("lucas", "position do listView: " + position);

        switch (position) {
            case 0:
                if (mListTickersBraziliex.btc_brl.getLast() >= mDefaultValue) {
                    showNotification("Bitcoin - BTC");
                }
                break;
            case 1:
                if (mListTickersBraziliex.eth_brl.getLast() >= mDefaultValue) {
                    showNotification("Ethereum - ETH");
                }
                break;
            case 2:
                if (mListTickersBraziliex.sngls_brl.getLast() >= mDefaultValue) {
                    Log.i("lucas", "valor singular: " + mListTickersBraziliex.sngls_brl.getLast() + "\ntxtValor: " + mDefaultValue);
                    showNotification("SingularDTV - SNGLS");
                }
                break;
            case 3:
                if (mListTickersBraziliex.dash_brl.getLast() >= mDefaultValue) {
                    showNotification("DASH");
                }
                break;
            case 4:
                if (mListTickersBraziliex.ltc_brl.getLast() >= mDefaultValue) {
                    showNotification("Litcoin - LTC");
                }
                break;
            case 5:
                if (mListTickersBraziliex.bch_brl.getLast() >= mDefaultValue) {
                    showNotification("Bitcoin cash - BCH");
                }
                break;
            case 6:
                if (mListTickersBraziliex.zec_brl.getLast() >= mDefaultValue) {
                    showNotification("ZCash - ZEC");
                }
                break;
            case 7:
                if (mListTickersBraziliex.crw_brl.getLast() >= mDefaultValue) {
                    showNotification("Crown - CRW");
                }
                break;
            case 8:
                if (mListTickersBraziliex.btg_brl.getLast() >= mDefaultValue) {
                    showNotification("Bitcoin gold - BTG");
                }
                break;
            case 9:
                if (mListTickersBraziliex.mxt_brl.getLast() >= mDefaultValue) {
                    showNotification("MarteXCoin - MXT");
                }
                break;
            case 10:
                if (mListTickersBraziliex.xmr_brl.getLast() >= mDefaultValue) {
                    showNotification("Monero - XMR");
                }
                break;
        }
    }

    //Exibe notificacao para os valor iguais ou menores que o definido
    public void lessThan(int position){

        switch (position) {
            case 0:
                if (mListTickersBraziliex.btc_brl.getLast() <= mDefaultValue) {
                    showNotification("Bitcoin - BTC");
                }
                break;
            case 1:
                if (mListTickersBraziliex.eth_brl.getLast() <= mDefaultValue) {
                    showNotification("Ethereum - ETH");
                }
                break;
            case 2:
                if (mListTickersBraziliex.sngls_brl.getLast() <= mDefaultValue) {
                    Log.i("lucas", "valor singular: " + mListTickersBraziliex.sngls_brl.getLast() + "\ntxtValor: " + mDefaultValue);
                    showNotification("SingularDTV - SNGLS");
                }
                break;
            case 3:
                if (mListTickersBraziliex.dash_brl.getLast() <= mDefaultValue) {
                    showNotification("DASH");
                }
                break;
            case 4:
                if (mListTickersBraziliex.ltc_brl.getLast() <= mDefaultValue) {
                    showNotification("Litcoin - LTC");
                }
                break;
            case 5:
                if (mListTickersBraziliex.bch_brl.getLast() <= mDefaultValue) {
                    showNotification("Bitcoin cash - BCH");
                }
                break;
            case 6:
                if (mListTickersBraziliex.zec_brl.getLast() <= mDefaultValue) {
                    showNotification("ZCash - ZEC");
                }
                break;
            case 7:
                if (mListTickersBraziliex.crw_brl.getLast() <= mDefaultValue) {
                    showNotification("Crown - CRW");
                }
                break;
            case 8:
                if (mListTickersBraziliex.btg_brl.getLast() <= mDefaultValue) {
                    showNotification("Bitcoin gold - BTG");
                }
                break;
            case 9:
                if (mListTickersBraziliex.mxt_brl.getLast() <= mDefaultValue) {
                    showNotification("MarteXCoin - MXT");
                }
                break;
            case 10:
                if (mListTickersBraziliex.xmr_brl.getLast() <= mDefaultValue) {
                    showNotification("Monero - XMR");
                }
                break;
        }
    }

    //Emite a notificacao
    public void showNotification(String coinName){
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

        PendingIntent intent = PendingIntent.getActivity(mContext, 0, new Intent(mContext, MainActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);

        builder.setTicker(coinName + " Chegou ao preço definido !!!");
        builder.setContentText(coinName + " Chegou ao preço definido !!!");
        builder.setSmallIcon(R.mipmap.ic_launcher);

        android.app.Notification notification = builder.build();
        notification.vibrate = new long[]{150, 300, 150, 600};

        notificationManager.notify(R.mipmap.ic_launcher, notification);

        try {

            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(mContext, som);
            toque.play();


        }catch (Exception e){
            Log.i("lucas", "Erro ao tocar som: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
