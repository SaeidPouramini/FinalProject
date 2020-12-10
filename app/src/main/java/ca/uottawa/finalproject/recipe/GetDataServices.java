package ca.uottawa.finalproject.recipe;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataServices {


    @GET("api/")
    Call<JsonObject> getData(
    );






}


