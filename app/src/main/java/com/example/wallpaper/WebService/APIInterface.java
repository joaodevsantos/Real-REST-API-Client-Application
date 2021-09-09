package com.example.wallpaper.WebService;

import com.example.wallpaper.Models.Collection;
import com.example.wallpaper.Models.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIInterface {
    @GET("photos/")
    Call<List<Photo>> getPhotos();

    @GET("collections")
    Call<List<Collection>> getCollections();

    @GET("collection/{id}")
    Call<Collection> getCollectionById(@Path("id") int id);

    @GET("collection/{id}/photos")
    Call<List<Photo>> getPhotosOfCollection(@Path("id") int id);

    @GET("photos/{id}")
    Call<Photo> getPhoto(@Path("id") String id);
}
