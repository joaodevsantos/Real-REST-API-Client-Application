package com.example.wallpaper.WebService;

import com.example.wallpaper.Models.Collection;
import com.example.wallpaper.Models.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("photos/")
    Call<List<Photo>> getPhotos();

    @GET("collection/featured/")
    Call<List<Collection>> getCollections();


}