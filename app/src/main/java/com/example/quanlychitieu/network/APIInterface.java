package com.example.quanlychitieu.network;


import com.example.quanlychitieu.model.Item;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    String path = "api";

    @GET(path +"/list")
    Call<List<Item>> getAllItem();

    @GET(path +"/today")
    Call<List<Item>> getTodayItem();

    @GET(path +"/list/{id}")
    Call<Item> getItemById(@Path("id") int id);

    @POST(path + "/store")
    Call<Item> createItem(@Body Item item);

    @POST(path + "/update")
    Call<Item> updateItem(@Body Item item);

    @DELETE(path + "/delete/{id}")
    Call<Void> deleteItem(@Path("id") int id);


    @POST(path + "/search")
    Call<List<Item>> searchTitle(@Query("title") String key);

}


