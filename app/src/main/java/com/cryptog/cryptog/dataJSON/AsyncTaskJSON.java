package com.cryptog.cryptog.dataJSON;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import com.cryptog.cryptog.dataDB.DataCollection;
import com.cryptog.cryptog.model.Coin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AsyncTaskJSON extends AsyncTask<Void,Void,Void> {


    private ArrayAdapter adapterListView;
    private List<Coin> coins;

    public AsyncTaskJSON(ArrayAdapter adapterListView, List<Coin> coins) {
        this.adapterListView = adapterListView;
        this.coins = coins;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        HttpHandler httpHandler = new HttpHandler();


        if(coins.size() != 0) {
            for (Coin coin : coins) {

                String formatedURL = "https://min-api.cryptocompare.com/data/price?fsym=" + coin.getSymbol() + "&tsyms=CAD,BRL";
                String jsonStr = httpHandler.makeServiceCall(formatedURL);

                if (jsonStr != null) {

                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        if (jsonObj.getDouble("CAD") != 0) {
                            coin.setConversionValueCAD(jsonObj.getDouble("CAD"));
                        }

                        if (jsonObj.getDouble("BRL") != 0) {
                            coin.setConversionValueBRL(jsonObj.getDouble("BRL"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {


                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        adapterListView.notifyDataSetChanged();
    }
}
