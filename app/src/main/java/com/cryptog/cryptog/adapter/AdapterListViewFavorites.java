package com.cryptog.cryptog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.cryptog.cryptog.R;
import com.cryptog.cryptog.model.Coin;
import java.util.List;

public class AdapterListViewFavorites extends ArrayAdapter<Coin> {

    private Context context;
    private List<Coin> coins;
    private String typeCoinversionString;

    public AdapterListViewFavorites(Context context,List<Coin> coins, String typeCoinversionString){
        super(context, R.layout.row_listview_favorites,coins);
        this.context = context;
        this.coins = coins;
        this.typeCoinversionString = typeCoinversionString;
    }

    public void changeFlag(String typeCoinversion){
        typeCoinversionString = typeCoinversion;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.row_listview_favorites,parent,false);

        TextView typeConversion = rootView.findViewById(R.id.typeConversionId);
        TextView coinName = rootView.findViewById(R.id.coinNameId);
        TextView valueConversion = rootView.findViewById(R.id.valueConversionId);
        TextView symbolCurrency = rootView.findViewById(R.id.textViewSymbolCurrencyFavoritesId);

        coinName.setText(coins.get(position).getFullName());
        typeConversion.setText(typeCoinversionString);

        if(typeCoinversionString.equalsIgnoreCase("CAD")){
            valueConversion.setText(Double.toString(coins.get(position).getConversionValueCAD()));
            symbolCurrency.setText("CA$");
        }else{
            valueConversion.setText(Double.toString(coins.get(position).getConversionValueBRL()));
            symbolCurrency.setText("R$");
        }

        return rootView;
    }

}
