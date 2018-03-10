package com.wb.tracun.braziliextest.Models;

/**
 * Created by Tracun on 15/02/2018.
 */

public class TickerBinance {

    private String symbol;
    private String lastQty;
    private long count;
    private long fristId;
    private String prevClosePrice;
    private String lastPrice;
    private String openTime;
    private String openPrice;
    private String quoteVolume;
    private String weightedAvgPrice;
    private String priceChange;
    private String lowPrice;
    private String bidPrice;
    private String closeTime;
    private String priceChangePercent;
    private long lastId;
    private String volume;
    private String highPrice;
    private String askPrice;

    private float lastPriceDollar;
    private float lastPriceReal;
    private int logo;
    private String name;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLastQty() {
        return lastQty;
    }

    public void setLastQty(String lastQty) {
        this.lastQty = lastQty;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getFristId() {
        return fristId;
    }

    public void setFristId(long fristId) {
        this.fristId = fristId;
    }

    public String getPrevClosePrice() {
        return prevClosePrice;
    }

    public void setPrevClosePrice(String prevClosePrice) {
        this.prevClosePrice = prevClosePrice;
    }

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getQuoteVolume() {
        return quoteVolume;
    }

    public void setQuoteVolume(String quoteVolume) {
        this.quoteVolume = quoteVolume;
    }

    public String getWeightedAvgPrice() {
        return weightedAvgPrice;
    }

    public void setWeightedAvgPrice(String weightedAvgPrice) {
        this.weightedAvgPrice = weightedAvgPrice;
    }

    public String getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(String priceChange) {
        this.priceChange = priceChange;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getPriceChangePercent() {
        return priceChangePercent;
    }

    public void setPriceChangePercent(String priceChangePercent) {
        this.priceChangePercent = priceChangePercent;
    }

    public long getLastId() {
        return lastId;
    }

    public void setLastId(long lastId) {
        this.lastId = lastId;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(String askPrice) {
        this.askPrice = askPrice;
    }

    public float getLastPriceDollar() {
        return lastPriceDollar;
    }

    public void setLastPriceDollar(float lastPriceDollar) {
        this.lastPriceDollar = lastPriceDollar;
    }

    public float getLastPriceReal() {
        return lastPriceReal;
    }

    public void setLastPriceReal(float lastPriceReal) {
        this.lastPriceReal = lastPriceReal;
    }

    @Override
    public String toString()
    {
        return "TickerBinance [lastQty = "+lastQty+", count = "+count+", fristId = "+fristId+", symbol = "+symbol+", prevClosePrice = "+prevClosePrice+", lastPrice = "+lastPrice+", openTime = "+openTime+", openPrice = "+openPrice+", quoteVolume = "+quoteVolume+", weightedAvgPrice = "+weightedAvgPrice+", priceChange = "+priceChange+", lowPrice = "+lowPrice+", bidPrice = "+bidPrice+", closeTime = "+closeTime+", priceChangePercent = "+priceChangePercent+", lastId = "+lastId+", volume = "+volume+", highPrice = "+highPrice+", askPrice = "+askPrice+"]";
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
