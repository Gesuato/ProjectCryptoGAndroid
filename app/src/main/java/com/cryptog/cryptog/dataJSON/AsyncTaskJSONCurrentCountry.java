package com.cryptog.cryptog.dataJSON;

import android.os.AsyncTask;
import com.cryptog.cryptog.adapter.AdapterListViewCountryOfVisit;
import com.cryptog.cryptog.dataDB.DataCollection;
import com.cryptog.cryptog.model.Coin;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AsyncTaskJSONCurrentCountry extends AsyncTask<Void,Void,Void> {

    private AdapterListViewCountryOfVisit adapterListView;
    private List<Coin> coins;

    public AsyncTaskJSONCurrentCountry(AdapterListViewCountryOfVisit adapterListView, List<Coin> coins) {
        this.adapterListView = adapterListView;
        this.coins = coins;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            HttpHandler httpHandler = new HttpHandler();

            for(Coin coin : coins) {

                if(DataCollection.getCountryName() != null){

                    String formatedURL = "https://min-api.cryptocompare.com/data/price?fsym="+coin.getSymbol()+"&tsyms=CAD,BRL,"+ DataCollection.getCountryCurrency();
                    String jsonStr = httpHandler.makeServiceCall(formatedURL);

                    if (jsonStr != null) {

                        try {
                            JSONObject jsonObj = new JSONObject(jsonStr);

                            if(jsonObj.getDouble(DataCollection.getCountryCurrency()) != 0){
                                coin.setConversionCountryOfVisit(jsonObj.getDouble(DataCollection.getCountryCurrency()));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        adapterListView.notifyDataSetChanged();
    }
}
