package com.wb.tracun.braziliextest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.wb.tracun.braziliextest.Models.Asks;
import com.wb.tracun.braziliextest.Models.Bids;
import com.wb.tracun.braziliextest.Models.OrderProperties;
import com.wb.tracun.braziliextest.Models.ResponsePrivateAPI;
import com.wb.tracun.braziliextest.Services.ApiService;
import com.wb.tracun.braziliextest.Services.Encode;
import com.wb.tracun.braziliextest.Services.ManageApi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderBookActivity extends AppCompatActivity {

    ListView listBuyer;
    ListView listSeller;
    Spinner spinner;
    List<String> list;

    public final String API_SECRET = "d3139fac30a3e4242f62dbe3d01c04975d221ddd788d7d0e8e88e57a0e675fee851f47632f03e924eb419fbc27e2ec9ff4113fce43585f77b873e4ae8a3789b7";
    public final String API_KEY = "47911a876cd5a652200ffc4925c9c87347a7e44f3b6df42eeb274acf5d04875a3fe1003b1e00f31ed6eaea1af2565a15732cd80fbe6aee1567028fc54b2cd8d9";
    public final String BASE_URL = "https://braziliex.com/api/v1/";

    public final int mDelay = 4000;
    public final int mInterval = 8000;

    Timer timer = new Timer();

    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_book);

        listBuyer = (ListView) findViewById(R.id.listBuyer);
        listSeller = (ListView) findViewById(R.id.listSeller);
        spinner = (Spinner) findViewById(R.id.spinner);

        createListSpinner();
        try{
            privateApi();
        }catch (Exception e){
            Log.e("Private", "Erro 1 Catch: " + e.getMessage());
            e.printStackTrace();
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //***Verificar necessidade
                catchOrderAsksBook();
                catchOrderBidsBook();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                timer();
            }
        });

        thread.start();
    }

    synchronized public void timer(){

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if(!verifyConnection()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.no_connection_internet,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    //Exibe ordens compradoras
                    catchOrderAsksBook();
                    //Exibe ordens vendedoras
                    catchOrderBidsBook();
                }
            }
        }, mDelay, mInterval);
    }

    void catchOrderAsksBook() {

        ManageApi manageApi = new ManageApi();

        try {
            Asks asks = manageApi.getAskOrderBraziliex(spinner.getSelectedItem().toString());
            final List<String> orderList = createOrderList(asks.asks);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), R.layout.simple_list_item_text_smaller, orderList);
                    listBuyer.setAdapter(adapter);
                }
            });

        }catch (Exception e){

            Log.e("Lucas", "Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    void catchOrderBidsBook() {

        ManageApi manageApi = new ManageApi();

        try {
            Bids bids = manageApi.getBidOrderBraziliex(spinner.getSelectedItem().toString());
            final List<String> orderList = createOrderList(bids.bids);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), R.layout.simple_list_item_text_smaller, orderList);
                    listSeller.setAdapter(adapter);
                }
            });

        }catch (Exception e){

            Log.e("Lucas", "Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    void createListSpinner(){
        list = new ArrayList<>();
        list.add("btc_brl");
        list.add("eth_brl");
        list.add("xmr_brl");
        list.add("dash_brl");
        list.add("ltc_brl");
        list.add("bch_brl");
        list.add("zec_brl");
        list.add("crw_brl");
        list.add("btg_brl");
        list.add("mxt_brl");
        list.add("sngls_brl");

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.simple_list_item_text_smaller, list);

        spinner.setAdapter(adapter);
    }

    List<String> createOrderList(List<OrderProperties> list){
        List<String> orderList = new ArrayList<>();

        for (OrderProperties op: list) {
            orderList.add("Price: R$: "+ op.getPrice() + "\nQtde: " + op.getAmount() + "\nTotal: R$" +
                    op.getPrice() * op.getAmount());
        }

        return orderList;
    }

    //Verifica conexao com internet **Colocar em uma classe separado
    boolean verifyConnection(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected()){
            return true;
        }

        return false;
    }

    void privateApi() throws Exception{

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiService apiService = retrofit.create(ApiService.class);

        /**
         *$req = array{'command'=>'balance'};
         *Traducao do trecho http_build_query($req, '', '&') em PHP
         * command=balance&nonce={int maior que o anterior}
         */

        //Concatena ao 'nonce' um inteiro sempre maior que o anterior
        String sign = "command=balance&nonce=" + new Date().getTime();

        Log.i("Private", "Chave em HASH512: " + Encode.toHMACSHA512(sign, API_SECRET) + " DATA: " + new Date().getTime());

        Call<ResponsePrivateAPI> call = apiService.balance(API_KEY, Encode.toHMACSHA512(sign, API_SECRET));

        call.enqueue(new Callback<ResponsePrivateAPI>() {
            @Override
            public void onResponse(Call<ResponsePrivateAPI> call, Response<ResponsePrivateAPI> response) {
                if(response.isSuccessful()){
                    Log.e("Private", "Sucesso Call: " + response.body().getSuccess() + " - MSG: " + response.body().getMessage());
                }else{
                    Log.e("Private", "Erro: Sucesso Call: " + response.body());
                }
            }
            @Override
            public void onFailure(Call<ResponsePrivateAPI> call, Throwable t) {
                Log.e("Private", "Erro: OnFailure " + t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
