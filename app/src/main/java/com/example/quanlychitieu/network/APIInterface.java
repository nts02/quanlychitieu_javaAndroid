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

    //lấy toàn bộ item
    @GET(path +"/list")
    Call<List<Item>> getAllItem();

    @GET(path +"/today")
    Call<List<Item>> getTodayItem();

    @GET(path +"/list/{id}")
    Call<Item> getItemById(@Path("id") int id);
//
//    @POST(path + "/save")
//    Call<Food> saveFood(@Body Food food);
//
//    @POST(path + "/edit")
//    Call<Food> editFood(@Body Food food);
//
//    @GET(path + "/delete/{id}")
//    Call<Void> deleteFood(@Path("id") long id);
//
//    @GET(path + "/search/{key}")
//    Call<List<Food>> searchFood(@Path("key") String keyword);

}

