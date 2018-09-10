package com.cryptog.cryptog.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cryptog.cryptog.R;
import com.cryptog.cryptog.model.Coin;
import java.util.List;

public class AdapterListViewAddCoin extends BaseAdapter{

    private final Activity activity;
    private final List<Coin> coins;

    public AdapterListViewAddCoin(Activity activity, List<Coin> coins) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = activity.getLayoutInflater().inflate(R.layout.row_listview_add_coin,viewGroup,false);

        Coin coin = coins.get(i);

        TextView fullNameCoin = rootView.findViewById(R.id.textViewFullNameCoin);

        if(coin.getFullName() != null){
            fullNameCoin.setText(coin.getFullName());
        }

        return rootView;
    }

}
