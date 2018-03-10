package com.wb.tracun.braziliextest.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wb.tracun.braziliextest.Models.TickerBinance;
import com.wb.tracun.braziliextest.Models.TickerBraziliex;
import com.wb.tracun.braziliextest.R;
import com.wb.tracun.braziliextest.Services.FormatBR;

import java.util.List;

/**
 * Created by Tracun on 15/02/2018.
 */

public class AdapterCoinsBinance extends BaseAdapter {

    private final List<TickerBinance> crypto;
    private final LayoutInflater mlayoutInflater;
    private int mLogo;

    public AdapterCoinsBinance(Context context, List<TickerBinance> crypto) {
        this.crypto = crypto;
        this.mlayoutInflater = LayoutInflater.from(context);
        this.mLogo = R.drawable.ic_photo_black_24dp;
    }

    @Override
    public int getCount() {
        return crypto.size();
    }

    @Override
    public Object getItem(int position) {
        return crypto.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TickerBinance item = this.crypto.get(position);
        convertView = mlayoutInflater.inflate(R.layout.list_crypto_imagem, null);

        //pegando as referências das Views
        TextView txtName = (TextView)
                convertView.findViewById(R.id.txtName);
        TextView txtValue = (TextView)
                convertView.findViewById(R.id.txtValue);
        TextView txtPercent = (TextView)
                convertView.findViewById(R.id.txtPercent);
        TextView txtValorReais = (TextView)
                convertView.findViewById(R.id.txtValue2);
        ImageView imagem = (ImageView)
                convertView.findViewById(R.id.logo);

        if(item.getLogo() != 0){
            this.mLogo = item.getLogo();
        }else{
            this.mLogo = R.drawable.ic_photo_black_24dp;
        }

        //populando as Views
        txtName.setText("\t" + item.getSymbol());

        txtValue.setText("\t$: " + item.getLastPriceDollar());
        txtValorReais.setText("\t" + FormatBR.toReal(item.getLastPriceReal()));
        txtPercent.setText(item.getPriceChangePercent() + "%");

        if(Float.parseFloat(item.getPriceChangePercent()) > 0){
            txtPercent.setTextColor(mlayoutInflater.getContext().getResources().getColor(R.color.green));
        }else if(Float.parseFloat(item.getPriceChangePercent()) == 0){
            txtPercent.setTextColor(Color.BLACK);
        }else{
            txtPercent.setTextColor(Color.RED);
        }

        imagem.setImageResource(this.mLogo);

        return convertView;
    }
}
