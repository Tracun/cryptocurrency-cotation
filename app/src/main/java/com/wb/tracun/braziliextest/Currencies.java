package com.wb.tracun.braziliextest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by u4239 on 08/01/2018.
 */

public class Currencies {

    @SerializedName("name")
    @Expose
    private String nome;

    @SerializedName("withdrawFee")
    @Expose
    private Float txWithdrawalFee;

    @SerializedName("withdrawPercFee")
    @Expose
    private Float txWithdrawalPercentageFee;

    @SerializedName("minConf")
    @Expose
    private int minConf;

    @SerializedName("minAmount")
    @Expose
    private float minAmountTrade;

    @SerializedName("decimal")
    @Expose
    private float decimal;

    @SerializedName("active")
    @Expose
    private int active;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getTxWithdrawalFee() {
        return txWithdrawalFee;
    }

    public void setTxWithdrawalFee(Float txWithdrawalFee) {
        this.txWithdrawalFee = txWithdrawalFee;
    }

    public Float getTxWithdrawalPercentageFee() {
        return txWithdrawalPercentageFee;
    }

    public void setTxWithdrawalPercentageFee(Float txWithdrawalPercentageFee) {
        this.txWithdrawalPercentageFee = txWithdrawalPercentageFee;
    }

    public int getMinConf() {
        return minConf;
    }

    public void setMinConf(int minConf) {
        this.minConf = minConf;
    }

    public float getMinAmountTrade() {
        return minAmountTrade;
    }

    public void setMinAmountTrade(float minAmountTrade) {
        this.minAmountTrade = minAmountTrade;
    }

    public float getDecimal() {
        return decimal;
    }

    public void setDecimal(float decimal) {
        this.decimal = decimal;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
