package com.cryptog.cryptog.dataDB;

import com.cryptog.cryptog.model.Coin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataCollection implements Serializable {

    private static String countryCurrency;
    private static String countryName;
    private static String countrySymbolCurrency;

    private List<Coin> coins = new ArrayList<>();

    public DataCollection() {
    }

    public Coin getCoin(int index){
        return coins.get(index);
    }

    public void setCoin(Coin coin){
        coins.add(coin);
    }

    public  void removeCoin(int index){
        coins.remove(index);
    }

    public static String getCountryCurrency() {
        return countryCurrency;
    }

    public static void setCountryCurrency(String countryCurrency) {
        DataCollection.countryCurrency = countryCurrency;
    }

    public static String getCountryName() {
        return countryName;
    }

    public static void setCountryName(String countryName) {
        DataCollection.countryName = countryName;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }

    public static String getCountrySymbolCurrency() {
        return countrySymbolCurrency;
    }

    public static void setCountrySymbolCurrency(String countrySymbolCurrency) {
        DataCollection.countrySymbolCurrency = countrySymbolCurrency;
    }
}
