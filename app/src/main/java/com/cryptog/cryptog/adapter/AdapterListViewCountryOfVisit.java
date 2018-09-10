package com.cryptog.cryptog.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cryptog.cryptog.CountryOfVisit;
import com.cryptog.cryptog.R;
import com.cryptog.cryptog.dataDB.DataCollection;
import com.cryptog.cryptog.model.Coin;

import java.util.List;

public class AdapterListViewCountryOfVisit extends BaseAdapter{

    private final Activity activity;
    private final List<Coin> coins;

    public AdapterListViewCountryOfVisit(Activity activity, List<Coin> coins) {
        this.activity = activity;
        this.coins = coins;
    }

    @Override
    public int getCount() {
        return coins.size();
    }

    @Override
    public Object getItem(int i) {
        return coins.get(i);
    }

    @Override
    public long getItemId(int i) {
        return coins.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View rootView = activity.getLayoutInflater().inflate(R.layout.row_listview_countryofvisity,viewGroup,false);

        try {
            TextView typeConversion = rootView.findViewById(R.id.typeConversionCurrentCountryId);
            TextView coinName = rootView.findViewById(R.id.coinNameCurrentCountryId);
            TextView valueConversion = rootView.findViewById(R.id.valueConversionCurrentCountryId);
            TextView symbolCurrency = rootView.findViewById(R.id.textViewSymbolCurrencyCurrentCountryId);

            typeConversion.setText(DataCollection.getCountryCurrency());
            coinName.setText(coins.get(position).getFullName());
            valueConversion.setText(Double.toString(coins.get(position).getConversionCountryOfVisit()));
            symbolCurrency.setText(DataCollection.getCountrySymbolCurrency());
        }catch (Exception e){
            e.printStackTrace();
        }

        return rootView;
    }
}
