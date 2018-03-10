package com.wb.tracun.braziliextest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wb.tracun.braziliextest.Adapter.AdapterCoinsBraziliex;
import com.wb.tracun.braziliextest.Adapter.AdapterCoinsBinance;
import com.wb.tracun.braziliextest.Email.SendMail;
import com.wb.tracun.braziliextest.Models.ListTickersBraziliex;
import com.wb.tracun.braziliextest.Models.TickerBinance;
import com.wb.tracun.braziliextest.Models.TickerBraziliex;
import com.wb.tracun.braziliextest.Services.ManageBinanceList;
import com.wb.tracun.braziliextest.Services.FormatBR;
import com.wb.tracun.braziliextest.Services.ManageApi;
import com.wb.tracun.braziliextest.Services.Notification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kotlin.jvm.Synchronized;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int mPositionListViewSelected = 0;

    //Valor definido pelo usuario
    float mDefaultValue;

    public final int mDelay = 4000;
    public final int mInterval = 8000;
    private boolean mFlag = true;
    private boolean mAlertFlag = false;
    public int mGenerateReportInterval = 4000;
    public final int mGenerateReportDelay = 900;

    EditText txtEmail;
    EditText txtTempo;
    ListView listView;
    ListView listViewBinance;
    EditText txtValue;
    Button btnDefineAlert;
    Button btnSendEmail;
    CheckBox checkBox;
    Spinner spinnerCoin;
    Spinner spinnerExchance;
    LinearLayout linearListView;
    TextView txtStatus;
    DrawerLayout drawer;
    View includeMain;
    View includeRepost;
    View includeAlert;

    public AsyncTask<Void, String, Void> task;

    Activity thisActivity;

    Timer timer = new Timer();
    Timer timerReport = new Timer();
    Thread thread;

    final ManageApi manageApi = new ManageApi();

    ListTickersBraziliex mListTickersBraziliex;
    List<TickerBinance> mListTickersBinance = new ArrayList<>();
    List<String> preferredTickers;
    List<String> exchanceList = new ArrayList<>();
    Notification mNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initializeComponents();
        setSupportActionBar(toolbar);

        setSpinnerExchance();

        thisActivity = this;

        if(mFlag){
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    timer();
                }
            });

            thread.start();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        createListSpinner();

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btnSendEmail.getText().toString().equals("Ativar")){

                    if (txtTempo.getText().toString().equals("") || txtEmail.getText().toString().equals("")){
                        txtTempo.setError("Campo inválido");
                        txtEmail.setError("Campo inválido");
                    }else if(!txtEmail.getText().toString().contains("@")){
                        txtEmail.setError("Email inválido");
                    }else{
                        mGenerateReportInterval = (int)((Float.parseFloat(txtTempo.getText().toString()) * 60) * 60 * 1000);
                        Log.i("Report-Activity", "Ativado - mGenerateReportInterval: " + mGenerateReportInterval);
                        generateReport();
                        btnSendEmail.setText("Desativar");
                    }

                }else{
                    timerReport.cancel();
                    timerReport = new Timer();
                    Log.i("Report-Activity", "Timer cancelado: ");
                    btnSendEmail.setText("Ativar");
                }
            }
        });

        btnDefineAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mAlertFlag){
                    mAlertFlag = false;
                    btnDefineAlert.setText("Ativar");
                }else{

                    if(!txtValue.getText().toString().equals("")){
                        mAlertFlag = true;
                        btnDefineAlert.setText("Desativar");
                        mPositionListViewSelected = spinnerCoin.getSelectedItemPosition();
                        mDefaultValue = Float.parseFloat(txtValue.getText().toString());
                    }else{
                        txtValue.setError("Vazio !");
                    }
                }
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });

//        spinnerExchance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(position == 0){
//
//                    try {
//                        timer.cancel();
//                        timer = new Timer();
//
//                    }catch (Exception e){
//
//                    }
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            listView.setAdapter(null);
//                        }
//                    });
//
//                    timer.scheduleAtFixedRate(new TimerTask() {
//                        @Override
//                        public void run() {
//                            getLastTickersBraziliex();
//                        }
//
//                    }, mDelay, mInterval);
//                }else{
//                    try {
//                        timer.cancel();
//                        timer = new Timer();
//                    }catch (Exception e){
//
//                    }
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            listView.setAdapter(null);
//                        }
//                    });
//
//                    timer.scheduleAtFixedRate(new TimerTask() {
//                        @Override
//                        public void run() {
//
//                            getLastTickersBinance();
//                        }
//
//                    }, mDelay, mInterval);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    public void initializeComponents(){
        listView = (ListView) findViewById(R.id.listView);
        listViewBinance = (ListView) findViewById(R.id.listViewBinance);
        txtValue = (EditText) findViewById(R.id.txtValue);
        btnDefineAlert = (Button) findViewById(R.id.btnDefineAlert);
        btnSendEmail = (Button) findViewById(R.id.btnSendEmail);
        checkBox = (CheckBox) findViewById(R.id.checkBoxMaiorQ);
        spinnerCoin = (Spinner) findViewById(R.id.spinnerCoin);
        spinnerExchance = (Spinner) findViewById(R.id.spinnerExchance);
        linearListView = (LinearLayout) findViewById(R.id.linearListView);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtTempo = (EditText) findViewById(R.id.txtTempo);
        includeMain = findViewById(R.id.includeMain);
        includeRepost = findViewById(R.id.includeReport);
        includeAlert = findViewById(R.id.includeAlert);
    }

    void getAllLastTickers(){
        //Verifica se há conexao com a internet
        if(!verifyConnection()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtStatus.setText(R.string.no_connection_internet);
                    Toast.makeText(getApplicationContext(), R.string.no_connection_internet,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }else{

            try {
                mListTickersBraziliex = manageApi.getAllTickerBraziliex();
                mListTickersBraziliex.createList();
                mListTickersBraziliex.toDollar();

                preferredTickers = new ArrayList<>();

                preferredTickers.add("BTCUSDT");
                preferredTickers.add("ETHUSDT");
                preferredTickers.add("LTCUSDT");

                preferredTickers.add("ETHBTC");
                preferredTickers.add("SNGLSBTC");
                preferredTickers.add("LTCBTC");
                preferredTickers.add("IOTABTC");
                preferredTickers.add("DASHBTC");
                preferredTickers.add("BTGBTC");
                preferredTickers.add("XRPBTC");
                preferredTickers.add("XMRBTC");

                mListTickersBinance = manageApi.getPreferredTickerBinance(preferredTickers);
                ManageBinanceList.toDollar(mListTickersBinance);
                ManageBinanceList.toReal(mListTickersBinance);
                ManageBinanceList.addIcon(mListTickersBinance);

            }catch (IOException e){

            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //Braziliex
                    AdapterCoinsBraziliex adapterCoinsBraziliex = new AdapterCoinsBraziliex(getBaseContext(), mListTickersBraziliex.list);
                    listView.setAdapter(adapterCoinsBraziliex);


                    //Binance
                    AdapterCoinsBinance adapterCoinsBinance = new AdapterCoinsBinance(getBaseContext(), mListTickersBinance);
                    listViewBinance.setAdapter(adapterCoinsBinance);

                    txtStatus.setText("Atualizado às " + FormatBR.getTime());

                    if(mAlertFlag){
                        notification(mPositionListViewSelected);
                    }
                }
            });
        }
    }

    void getLastTickersBraziliex(){

        /**
         * Estudar esse codigo
         */
//        this.task = new AsyncTask<Void, String, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                return null;
//            }
//        };

        //Verifica se há conexao com a internet
        if(!verifyConnection()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtStatus.setText(R.string.no_connection_internet);
                    Toast.makeText(getApplicationContext(), R.string.no_connection_internet,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }else{

            try {
                mListTickersBraziliex = manageApi.getAllTickerBraziliex();
                mListTickersBraziliex.createList();
                mListTickersBraziliex.toDollar();

            }catch (IOException e){

            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    AdapterCoinsBraziliex adapterCoinsBraziliex = new AdapterCoinsBraziliex(getBaseContext(), mListTickersBraziliex.list);

                    adapterCoinsBraziliex.notifyDataSetChanged();
                    listView.setAdapter(adapterCoinsBraziliex);
                    listView.requestLayout();
                    txtStatus.setText("Atualizado às " + FormatBR.getTime());

                    if(mAlertFlag){
                        notification(mPositionListViewSelected);
                    }
                }
            });
        }
    }

    void getLastTickersBinance(){

        //Verifica se há conexao com a internet
        if(!verifyConnection()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtStatus.setText(R.string.no_connection_internet);
                    Toast.makeText(getApplicationContext(), R.string.no_connection_internet,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }else{

            try {
                preferredTickers = new ArrayList<>();

                preferredTickers.add("BTCUSDT");
                preferredTickers.add("ETHUSDT");
                preferredTickers.add("LTCUSDT");

                preferredTickers.add("ETHBTC");
                preferredTickers.add("SNGLSBTC");
                preferredTickers.add("LTCBTC");
                preferredTickers.add("IOTABTC");
                preferredTickers.add("DASHBTC");
                preferredTickers.add("BTGBTC");
                preferredTickers.add("XRPBTC");
                preferredTickers.add("XMRBTC");

                mListTickersBinance = manageApi.getPreferredTickerBinance(preferredTickers);
                ManageBinanceList.toDollar(mListTickersBinance);
                ManageBinanceList.toReal(mListTickersBinance);
                ManageBinanceList.addIcon(mListTickersBinance);

            }catch (IOException e){

            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    AdapterCoinsBinance adapterCoinsBinance = new AdapterCoinsBinance(getBaseContext(), mListTickersBinance);

                    adapterCoinsBinance.notifyDataSetChanged();
                    listViewBinance.setAdapter(adapterCoinsBinance);
                    listViewBinance.requestLayout();
                    txtStatus.setText("Atualizado às " + FormatBR.getTime());

                    if(mAlertFlag){
                        notification(mPositionListViewSelected);
                    }
                }
            });
        }
    }

    synchronized public void timer(){

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getAllLastTickers();
            }

        }, mDelay, mInterval);
    }

    void notification(int position){

        mNotification = new Notification(mListTickersBraziliex, mDefaultValue, this);

        if(txtValue.getText().toString().equals("")){
            txtValue.setError("Campo vazio");
        }else{
            if(checkBox.isChecked()){
                mNotification.moreThan(position);
            }else{
                mNotification.lessThan(position);
            }
        }
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

    void orderActivity(){
        Intent intent = new Intent(this, OrderBookActivity.class);
        startActivity(intent);
    }

    void mainActivity(){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    void createListSpinner(){
        ArrayList<Object> list = new ArrayList<>();
        list.add("btc_brl");
        list.add("eth_brl");
        list.add("sngls_brl");
        list.add("dash_brl");
        list.add("ltc_brl");
        list.add("bch_brl");
        list.add("zec_brl");
        list.add("crw_brl");
        list.add("btg_brl");
        list.add("mxt_brl");
        list.add("xmr_brl");

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.simple_list_item_text_smaller, list);

        spinnerCoin.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_quotation) {
            includeRepost.setVisibility(View.INVISIBLE);
            includeAlert.setVisibility(View.INVISIBLE);
            includeMain.setVisibility(View.VISIBLE);
        }else if (id == R.id.nav_orders) {
            orderActivity();
        }else if (id == R.id.nav_report) {
            includeMain.setVisibility(View.INVISIBLE);
            includeAlert.setVisibility(View.INVISIBLE);
            includeRepost.setVisibility(View.VISIBLE);

        }else if (id == R.id.nav_alert) {
            includeRepost.setVisibility(View.INVISIBLE);
            includeMain.setVisibility(View.INVISIBLE);
            includeAlert.setVisibility(View.VISIBLE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void generateReport() {

        timerReport.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String emailBody;

                        if (!txtEmail.getText().toString().equals("")) {

                            String email = txtEmail.getText().toString();

                            if (mListTickersBraziliex != null) {

                                Log.i("Report-Activity", "Nada é null");

                                mListTickersBraziliex.createList();

                                emailBody = "";

                                for (TickerBraziliex ticker : mListTickersBraziliex.list) {
                                    emailBody += ticker.getMarket() + " " + ticker.getLast() + " "
                                            + ticker.getBaseVolume24() + " " + ticker.getPercentChange()
                                            + " " + FormatBR.getTime() + "\n";
                                }
                                sendEmail(emailBody, email);
                            } else {
                                Log.i("Report-Activity", "ListTickersBraziliex is null");
                            }
                        } else {
                            Log.i("Report-Activity", "email is null");
                            txtEmail.setError("Vazio");
                        }
                    }
                });

            }
        }, mGenerateReportDelay, mGenerateReportInterval);
    }

    void sendEmail(String emailBody, String email){

        SendMail sm = new SendMail(this, email, "relatório Braziliex", emailBody);
        sm.execute();
        Log.i("Report-Activity", "Email enviado!");
    }

    void setSpinnerExchance(){
        exchanceList.add("Braziliex - BR");
        exchanceList.add("Binance - CHN");

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.simple_list_item_text_smaller, exchanceList);
        spinnerExchance.setAdapter(adapter);
    }
}
