package com.cryptog.cryptog.dataDB;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.cryptog.cryptog.model.Coin;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private List<CoinDB> coinDBList = new ArrayList<>();
    private CryptoGDatabase cryptoGDatabase;
    private Context context;

    public FileHandler(Context context) {
        this.context = context;
    }

    public void saveData(List<Coin> newListCoin){
        cryptoGDatabase = Room.databaseBuilder(context, CryptoGDatabase.class, "CryptoGDB").allowMainThreadQueries().build();

        for(Coin coin : newListCoin){
            CoinDB coinDB = new CoinDB(coin.getId(),coin.getCoinName(),coin.getFullName(),coin.getSymbol(),coin.getConversionValueCAD(),coin.getConversionValueBRL(),coin.getConversionCountryOfVisit());
            coinDBList.add(coinDB);
        }
        cryptoGDatabase.coinDao().insertAll(coinDBList);
    }

    public List<Coin> fetchData(){
        cryptoGDatabase = Room.databaseBuilder(context, CryptoGDatabase.class, "CryptoGDB").allowMainThreadQueries().build();
        coinDBList = cryptoGDatabase.coinDao().getAll();
        List<Coin> coinList = new ArrayList<>();
        if (coinDBList.size() > 0)
        {
            for (CoinDB coinDB : coinDBList) {
                Coin coin = new Coin();
                coin.setId(coinDB.getId());
                coin.setCoinName(coinDB.getCoinName());
                coin.setFullName(coinDB.getFullName());
                coin.setSymbol(coinDB.getSymbol());
                coin.setConversionValueCAD(coinDB.getConversionValueCAD());
                coin.setConversionValueBRL(coinDB.getConversionValueBRL());
                coin.setConversionCountryOfVisit(coinDB.getConversionCountryOfVisit());

                coinList.add(coin);
            }
        }
        return coinList;
    }

    public void removeCoin(Coin coin){
        cryptoGDatabase = Room.databaseBuilder(context, CryptoGDatabase.class, "CryptoGDB").allowMainThreadQueries().build();

            CoinDB coinDB = new CoinDB(coin.getId(),coin.getCoinName(),coin.getFullName(),coin.getSymbol(),coin.getConversionValueCAD(),coin.getConversionValueBRL(),coin.getConversionCountryOfVisit());
            coinDBList.remove(coinDB);

        cryptoGDatabase.coinDao().removeCoin(coinDB);
    }
}
