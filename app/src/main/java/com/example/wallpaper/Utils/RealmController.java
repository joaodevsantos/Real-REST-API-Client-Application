package com.example.wallpaper.Utils;

import com.example.wallpaper.Models.Photo;

import java.util.List;

import io.realm.Realm;

public class RealmController {
    private final Realm realm;

    public RealmController(){
        realm = Realm.getDefaultInstance();
    }

    public void savePhoto(Photo photo){
        realm.beginTransaction();
        realm.copyToRealm(photo);
        realm.commitTransaction();
    }

    public void deletePhoto(Photo photo){
        realm.executeTransaction(realm -> {
            Photo resultPhoto = realm.where(Photo.class)
                                        .equalTo("id", photo.getId())
                                        .findFirst();
            if(resultPhoto != null)
                resultPhoto.deleteFromRealm();
        });
    }

    public boolean isPhotoExists(String photoId){
        Photo resultPhoto = realm.where(Photo.class)
                .equalTo("id", photoId)
                .findFirst();

        return resultPhoto != null;
    }

    public List<Photo> getPhotos(){
        return realm.where(Photo.class).findAll();
    }
}
