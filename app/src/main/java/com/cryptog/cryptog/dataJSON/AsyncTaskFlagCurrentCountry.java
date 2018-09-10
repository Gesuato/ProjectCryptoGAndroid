package com.cryptog.cryptog.dataJSON;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.cryptog.cryptog.R;
import com.cryptog.cryptog.dataDB.DataCollection;

import java.net.URL;

public class AsyncTaskFlagCurrentCountry extends AsyncTask<Void,Void,Bitmap> {

    Activity activity;

    public AsyncTaskFlagCurrentCountry(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        Bitmap result = null;

        if(DataCollection.getCountryName() != null){
            try {

                String urlCurrentCountry = "https://assets.thebasetrip.com/api/v2/countries/flags/"+DataCollection.getCountryName()+".png";
                result = BitmapFactory.decodeStream(new URL(urlCurrentCountry).openStream());


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

        try {
            if(result != null){
                ImageView flagCurrentCountry = activity.findViewById(R.id.imageViewFlagCurrentCountry);
                flagCurrentCountry.setImageBitmap(result);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
