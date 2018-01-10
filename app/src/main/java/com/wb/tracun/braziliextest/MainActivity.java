package com.wb.tracun.braziliextest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText txtValue;
    Button btnDefineAlert;
    CheckBox checkBox;

    Timer timer = new Timer();
    Thread thread = new Thread();

    Currency mCurrency;

    String[] mLista;

    int mPositionListViewSelected = 0;

    //Valor definido pelo usuario
    float mDefaultValue;

    public final int mDelay = 5000;
    public final int mInterval = 5000;
    private boolean mFlag = true;
    private boolean mAlertFlag = false;


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        recreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        txtValue = (EditText) findViewById(R.id.txtValue);
        btnDefineAlert = (Button) findViewById(R.id.btnDefineAlert);
        checkBox = (CheckBox) findViewById(R.id.checkBoxMaiorQ);

        btnDefineAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertFlag = false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Intent intent = new Intent(getBaseContext(), GraphActivity.class);
//                startActivity(intent);
                mAlertFlag = true;
                mPositionListViewSelected = position;
                mDefaultValue = Float.parseFloat(txtValue.getText().toString());
            }
        });

        if(mFlag){
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    timer();
                }
            });

            thread.start();
        }
    }
    synchronized public void timer(){

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                //Verifica conexao com internet
                if(!verifyConnection()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Verifique conexão com a internet !",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    //Verifica se a atualizacao automatica esta habilidade atraves do boolean "mFlag"
                    if(mFlag){
                        ManageApi manageApi = new ManageApi();
                        mLista = manageApi.getTicker();

                        mCurrency = manageApi.mCurrency;

                        if(mLista != null){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    ArrayAdapter adapter = new ArrayAdapter(getBaseContext(),
                                            android.R.layout.simple_list_item_1, mLista);
                                    listView.setAdapter(adapter);
                                    Toast.makeText(getApplicationContext(), "Atualizado", Toast.LENGTH_SHORT).show();

                                    if(mAlertFlag){
                                        notification(mPositionListViewSelected);
                                    }
                                }
                            });
                        }
                    }
                }
            }

        }, mDelay, mInterval);
    }

    void notification(int position){

        if(txtValue.getText().toString().equals("")){
            txtValue.setError("Campo vazio");
        }else{
            if(checkBox.isChecked()){
                moreThan(position);
            }else{
                lessThan(position);
            }
        }
    }

    //Exibe notificacao para os valor iguais ou maiores que o definido
    void moreThan(int position){

        Log.i("lucas", "position do listView: " + position);

        switch (position) {
            case 0:
                if (mCurrency.btc_brl.getLast() >= mDefaultValue) {
                    showNotification("Bitcoin - BTC");
                }
                break;
            case 1:
                if (mCurrency.eth_brl.getLast() >= mDefaultValue) {
                    showNotification("Ethereum - ETH");
                }
                break;
            case 2:
                if (mCurrency.sngls_brl.getLast() >= mDefaultValue) {
                    Log.i("lucas", "valor singular: " + mCurrency.sngls_brl.getLast() + "\ntxtValor: " + mDefaultValue);
                    showNotification("SingularDTV - SNGLS");
                }
                break;
            case 3:
                if (mCurrency.dash_brl.getLast() >= mDefaultValue) {
                    showNotification("DASH");
                }
                break;
            case 4:
                if (mCurrency.ltc_brl.getLast() >= mDefaultValue) {
                    showNotification("Litcoin - LTC");
                }
                break;
            case 5:
                if (mCurrency.bch_brl.getLast() >= mDefaultValue) {
                    showNotification("Bitcoin cash - BCH");
                }
                break;
            case 6:
                if (mCurrency.zec_brl.getLast() >= mDefaultValue) {
                    showNotification("ZCash - ZEC");
                }
                break;
            case 7:
                if (mCurrency.crw_brl.getLast() >= mDefaultValue) {
                    showNotification("Crown - CRW");
                }
                break;
            case 8:
                if (mCurrency.btg_brl.getLast() >= mDefaultValue) {
                    showNotification("Bitcoin gold - BTG");
                }
                break;
            case 9:
                if (mCurrency.mxt_brl.getLast() >= mDefaultValue) {
                    showNotification("MarteXCoin - MXT");
                }
                break;
            case 10:
                if (mCurrency.xmr_brl.getLast() >= mDefaultValue) {
                    showNotification("Monero - XMR");
                }
                break;
        }
    }

    //Exibe notificacao para os valor iguais ou menores que o definido
    void lessThan(int position){

        switch (position) {
            case 0:
                if (mCurrency.btc_brl.getLast() <= mDefaultValue) {
                    showNotification("Bitcoin - BTC");
                }
                break;
            case 1:
                if (mCurrency.eth_brl.getLast() <= mDefaultValue) {
                    showNotification("Ethereum - ETH");
                }
                break;
            case 2:
                if (mCurrency.sngls_brl.getLast() <= mDefaultValue) {
                    Log.i("lucas", "valor singular: " + mCurrency.sngls_brl.getLast() + "\ntxtValor: " + mDefaultValue);
                    showNotification("SingularDTV - SNGLS");
                }
                break;
            case 3:
                if (mCurrency.dash_brl.getLast() <= mDefaultValue) {
                    showNotification("DASH");
                }
                break;
            case 4:
                if (mCurrency.ltc_brl.getLast() <= mDefaultValue) {
                    showNotification("Litcoin - LTC");
                }
                break;
            case 5:
                if (mCurrency.bch_brl.getLast() <= mDefaultValue) {
                    showNotification("Bitcoin cash - BCH");
                }
                break;
            case 6:
                if (mCurrency.zec_brl.getLast() <= mDefaultValue) {
                    showNotification("ZCash - ZEC");
                }
                break;
            case 7:
                if (mCurrency.crw_brl.getLast() <= mDefaultValue) {
                    showNotification("Crown - CRW");
                }
                break;
            case 8:
                if (mCurrency.btg_brl.getLast() <= mDefaultValue) {
                    showNotification("Bitcoin gold - BTG");
                }
                break;
            case 9:
                if (mCurrency.mxt_brl.getLast() <= mDefaultValue) {
                    showNotification("MarteXCoin - MXT");
                }
                break;
            case 10:
                if (mCurrency.xmr_brl.getLast() <= mDefaultValue) {
                    showNotification("Monero - XMR");
                }
                break;
        }
    }

    //Emite a notificacao
    void showNotification(String coinName){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setTicker(coinName + "Chegou ao preço definido !!!");
        builder.setContentText(coinName + "Chegou ao preço definido !!!");
        builder.setSmallIcon(R.mipmap.ic_launcher);

        Notification notification = builder.build();
        notification.vibrate = new long[]{150, 300, 150, 600};

        notificationManager.notify(R.mipmap.ic_launcher, notification);

        try {

            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(this, som);
            toque.play();


        }catch (Exception e){
            Log.i("lucas", "Erro ao tocar som: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Verifica conexao com internet
    boolean verifyConnection(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected()){
            return true;
        }

        return false;
    }



}