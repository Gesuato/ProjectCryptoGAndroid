package com.cryptog.cryptog;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.cryptog.cryptog.adapter.AdapterListViewCountryOfVisit;
import com.cryptog.cryptog.dataDB.DataCollection;
import com.cryptog.cryptog.dataJSON.AsyncTaskFlagCurrentCountry;
import com.cryptog.cryptog.dataJSON.AsyncTaskJSONCurrentCountry;

/**
 * A simple {@link Fragment} subclass.
 *
 */


public class CountryOfVisit extends Fragment {

    private TextView textViewCurrencyCountry;
    private ListView listViewCurrentCountry;
    private AdapterListViewCountryOfVisit currentCountryAdapter;
    private DataCollection dataCollection;
    private ImageView imageViewCurrentCountry;

    public CountryOfVisit() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_country_of_visit, container, false);

            textViewCurrencyCountry = rootView.findViewById(R.id.textViewCurrentCountryId);
            textViewCurrencyCountry.setText(DataCollection.getCountryCurrency());
            listViewCurrentCountry = rootView.findViewById(R.id.listViewCurrentCountry);
            imageViewCurrentCountry = rootView.findViewById(R.id.imageViewFlagCurrentCountry);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            imageViewCurrentCountry.getLayoutParams().height = 200;
            textViewCurrencyCountry.setTextSize(20);
        }else{
            imageViewCurrentCountry.getLayoutParams().height = 120;
            textViewCurrencyCountry.setTextSize(15);

        }
            return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dataCollection = ((MainActivity) getActivity()).getDataCollection();
        currentCountryAdapter = new AdapterListViewCountryOfVisit((Activity) CountryOfVisit.super.getContext(), dataCollection.getCoins());
        try {
            new AsyncTaskJSONCurrentCountry(currentCountryAdapter, dataCollection.getCoins()).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        listViewCurrentCountry.setAdapter(currentCountryAdapter);

        try {
            new AsyncTaskFlagCurrentCountry(CountryOfVisit.super.getActivity()).execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            currentCountryAdapter.notifyDataSetChanged();
            new AsyncTaskJSONCurrentCountry(currentCountryAdapter, dataCollection.getCoins()).execute();
        }
    }
}
