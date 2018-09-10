package com.cryptog.cryptog.dataJSON.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoCoinServer {

    @GET("ticker/?limit=200&structure=array")
    Call<DataJSON> data();
}
