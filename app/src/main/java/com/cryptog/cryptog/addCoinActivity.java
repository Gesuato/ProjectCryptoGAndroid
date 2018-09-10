package com.cryptog.cryptog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.cryptog.cryptog.adapter.AdapterListViewAddCoin;
import com.cryptog.cryptog.dataDB.FileHandler;
import com.cryptog.cryptog.dataJSON.retrofit.CoinJSON;
import com.cryptog.cryptog.dataJSON.retrofit.CryptoCoinServer;
import com.cryptog.cryptog.dataJSON.retrofit.DataJSON;
import com.cryptog.cryptog.model.Coin;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class addCoinActivity extends AppCompatActivity {

    private Button btnCancel;
    private ListView listViewAdd;
    private List<Coin> coins = new ArrayList<>();
    AdapterListViewAddCoin adapterListViewAddCoin;
    private List<Coin> currentListCoin ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coin);

        currentListCoin = (List<Coin>) getIntent().getSerializableExtra("currentListCoin");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.coinmarketcap.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CryptoCoinServer service = retrofit.create(CryptoCoinServer.class);

        Call<DataJSON> requestData = service.data();

        requestData.enqueue(new Callback<DataJSON>() {
            @Override
            public void onResponse(Call<DataJSON> call, Response<DataJSON> response) {

                if(response.isSuccessful()){
                    DataJSON DataJSON = response.body();

                    for(CoinJSON coinJSON : DataJSON.data){
                        Coin coin = new Coin();
                        coin.setId(coinJSON.id);
                        String fullName = coinJSON.name + " (" + coinJSON.symbol + ")";
                        coin.setFullName(fullName);
                        coin.setSymbol(coinJSON.symbol);
                        coin.setCoinName(coinJSON.name);
                        coins.add(coin);
                    }
                    adapterListViewAddCoin.notifyDataSetChanged();


                }else{
                    System.out.println("Resposta com erro");
                }
            }

            @Override
            public void onFailure(Call<DataJSON> call, Throwable t) {
                System.out.println("Erro call DataJSON");
            }
        });

        btnCancel = findViewById(R.id.btnCancel);
        listViewAdd = findViewById(R.id.listViewAddCoin);

        adapterListViewAddCoin = new AdapterListViewAddCoin(addCoinActivity.this,coins);
        listViewAdd.setAdapter(adapterListViewAddCoin);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listViewAdd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                saveCoinInDB(coins.get(i));
            }
        });
    }

    public boolean validationCoin(Coin coin){
        boolean isValide = true;

        for(Coin coinValidation : currentListCoin){
            if(coin.getId() == coinValidation.getId()){
                isValide = false;
                break;
            }
        }
        return isValide;
    }

    public void saveCoinInDB(Coin coin){
        if(validationCoin(coin)){
//            DataCollection.setCoin(coin);

            List<Coin> currentListCoin = new ArrayList<>();

            currentListCoin = (List<Coin>) getIntent().getSerializableExtra("currentListCoin");
            currentListCoin.add(coin);
            Intent intent = new Intent(addCoinActivity.this,MainActivity.class);
            FileHandler fileHandler = new FileHandler(addCoinActivity.this);
            fileHandler.saveData(currentListCoin);
            Toast.makeText(addCoinActivity.this,"Vous avez ajouté : " + coin.getFullName(),Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(addCoinActivity.this,"Cette devise a déjà été ajoutée, choisissez une autre devise",Toast.LENGTH_LONG).show();
        }
    }

}
