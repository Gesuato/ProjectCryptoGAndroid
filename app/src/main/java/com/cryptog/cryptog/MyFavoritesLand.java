package com.cryptog.cryptog;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.cryptog.cryptog.adapter.AdapterListViewFavorites;
import com.cryptog.cryptog.dataDB.DataCollection;
import com.cryptog.cryptog.dataJSON.AsyncTaskJSON;

public class MyFavoritesLand extends Fragment {

    private ListView listViewFavorites;
    private ImageButton imageButtonBRL;
    private ImageButton imageButtonCAD;
    private String convertionSelected;
    private AdapterListViewFavorites adapter;
    DataCollection dataCollection;

    public MyFavoritesLand() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_favorites_land, container, false);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            convertionSelected = sharedPreferences.getString("convertionSelected", "CAD");
            imageButtonBRL = rootView.findViewById(R.id.imageButtonBRLLand);
            imageButtonCAD = rootView.findViewById(R.id.imageButtonCADLand);
            listViewFavorites = rootView.findViewById(R.id.listViewFavoritesLand);
            reloadFlag(convertionSelected);

            imageButtonBRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    convertionSelected = "BRL";
                    reloadFlag(convertionSelected);
                    adapter.changeFlag(convertionSelected);
                    adapter.notifyDataSetChanged();
                    SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("convertionSelected", convertionSelected).commit();
                }
            });

            imageButtonCAD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    convertionSelected = "CAD";
                    reloadFlag(convertionSelected);
                    adapter.changeFlag(convertionSelected);
                    adapter.notifyDataSetChanged();
                    SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("convertionSelected", convertionSelected).commit();
                }
            });


        return rootView;
        }else {
            return null;
        }
        }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dataCollection = ((MainActivity) getActivity()).getDataCollection();
        if (dataCollection.getCoins().size() > 0) {
            adapter = new AdapterListViewFavorites(MyFavoritesLand.super.getContext(), dataCollection.getCoins(), convertionSelected);
            new AsyncTaskJSON(adapter, dataCollection.getCoins()).execute();
            listViewFavorites.setAdapter(adapter);
        }

    }

    public void reloadFlag(String convertionSelected){

        if(convertionSelected.equalsIgnoreCase("CAD")) {
            imageButtonCAD.setImageResource(R.drawable.imagecanada);
            imageButtonBRL.setImageResource(R.drawable.imagebrasilnotselected);
        }else{
            imageButtonBRL.setImageResource(R.drawable.imagebrasil);
            imageButtonCAD.setImageResource(R.drawable.imagecanadanotselected);
        }
    }
}
