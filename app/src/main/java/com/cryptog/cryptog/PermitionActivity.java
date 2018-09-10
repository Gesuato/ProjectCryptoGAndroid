package com.cryptog.cryptog;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cryptog.cryptog.dataDB.DataCollection;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.Normalizer;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class PermitionActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationClient;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permition);

        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        findLastLocation();

    }

    private void findLastLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {

                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                                if (addresses.size() > 0) {
                                    DataCollection.setCountryName(removeAccents(addresses.get(0).getCountryName().toLowerCase()));
                                    Locale current = new Locale.Builder().setRegion(addresses.get(0).getCountryCode()).build();
                                    DataCollection.setCountryCurrency(Currency.getInstance(current).getCurrencyCode());
                                    DataCollection.setCountrySymbolCurrency(Currency.getInstance(current).getSymbol());
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        final Intent intent = new Intent(this,MainActivity.class);
        switch( requestCode ){
            case 1:
                for( int i = 0; i < permissions.length; i++ ){

                    if( permissions[i].equalsIgnoreCase( Manifest.permission.ACCESS_FINE_LOCATION )
                            && grantResults[i] == PackageManager.PERMISSION_GRANTED ){
                        findLastLocation();
                    }
                }
                startActivity(intent);
                finish();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public String removeAccents(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
