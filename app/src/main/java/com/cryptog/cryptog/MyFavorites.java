package com.cryptog.cryptog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import com.cryptog.cryptog.adapter.AdapterListViewFavorites;
import com.cryptog.cryptog.dataDB.DataCollection;
import com.cryptog.cryptog.dataDB.FileHandler;
import com.cryptog.cryptog.dataJSON.AsyncTaskJSON;
import com.cryptog.cryptog.model.Coin;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFavorites extends Fragment{

    private ListView listViewFavorites;
    private ImageButton imageButtonBRL;
    private ImageButton imageButtonCAD;
    private String convertionSelected;
    private AdapterListViewFavorites adapter;
    private Button btnNewCoin;
    private int currentPos;
    private Button btnRefresh;
    private DataCollection dataCollection;

    public MyFavorites() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_favorites, container, false);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            imageButtonBRL = rootView.findViewById(R.id.imageButtonBRL);
            imageButtonCAD = rootView.findViewById(R.id.imageButtonCAD);
            listViewFavorites = rootView.findViewById(R.id.listViewFavorites);
            btnNewCoin = rootView.findViewById(R.id.newCoinId);
            btnRefresh = rootView.findViewById(R.id.btnRefresh);

            SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            convertionSelected = sharedPreferences.getString("convertionSelected", "CAD");
            dataCollection = ((MainActivity) getActivity()).getDataCollection();
            reloadFlag(convertionSelected);

            if (dataCollection.getCoins().size() > 0) {
                adapter = new AdapterListViewFavorites(MyFavorites.super.getContext(), dataCollection.getCoins(), convertionSelected);
                new AsyncTaskJSON(adapter, dataCollection.getCoins()).execute();
            }

            adapter = new AdapterListViewFavorites(MyFavorites.super.getContext(), dataCollection.getCoins(), convertionSelected);

            listViewFavorites.setAdapter(adapter);
            listViewFavorites.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    AlertDialog dialaog = createDialog();
                    dialaog.show();
                    currentPos = i;
                    return true;
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

            btnNewCoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<Coin> currentList = new ArrayList<>();
                    currentList = dataCollection.getCoins();
                    Intent intent = new Intent(MyFavorites.super.getContext(), addCoinActivity.class);
                    intent.putExtra("currentListCoin", (Serializable) currentList);
                    startActivity(intent);

                }
            });

            btnRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AsyncTaskJSON(adapter, dataCollection.getCoins()).execute();
                }
            });

            return rootView;
        }else{
            return null;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            adapter.notifyDataSetChanged();
            new AsyncTaskJSON(adapter, dataCollection.getCoins()).execute();
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

    public AlertDialog createDialog(){

        AlertDialog.Builder  alertDialaog = new AlertDialog.Builder(MyFavorites.super.getContext());
        alertDialaog.setTitle("Supprimer la devise");
        alertDialaog.setMessage("Voulez-vous supprimer? ");

        alertDialaog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FileHandler fileHandler = new FileHandler(MyFavorites.super.getContext());
                fileHandler.removeCoin(dataCollection.getCoin(currentPos));
                dataCollection.removeCoin(currentPos);
                ((MainActivity)getActivity()).setDataCollection(dataCollection);
                ((MainActivity)getActivity()).notifyChangeListCoinDataCollection();
                adapter.notifyDataSetChanged();
            }
        });

        alertDialaog.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return alertDialaog.create();
    }
}
