package com.example.wallpaper.UI.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.wallpaper.Models.Photo;
import com.example.wallpaper.R;
import com.example.wallpaper.Utils.Functions;
import com.example.wallpaper.Utils.RealmController;
import com.example.wallpaper.WebService.APIInterface;
import com.example.wallpaper.WebService.ServiceGenerator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullscreenPhoto extends AppCompatActivity {

    private String TAG = FullscreenPhoto.class.getSimpleName();

    @BindView(R.id.activity_fullscreen_photo_photo)
    ImageView photo;
    @BindView(R.id.activity_fullscreen_photo_user_avatar)
    CircleImageView userAvatar;
    @BindView(R.id.activity_fullscreen_photo_username)
    TextView username;
    @BindView(R.id.activity_fullscreen_photo_fab_menu)
    FloatingActionButton floatMenu;
    @BindView(R.id.activity_fullscreen_photo_fab_favorite)
    FloatingActionButton floatFavorite;
    @BindView(R.id.activity_fullscreen_photo_fab_wallpaper)
    FloatingActionButton floatWallpaper;

    @BindDrawable(R.drawable.ic_check_favorite)
    Drawable icFavorite;
    @BindDrawable(R.drawable.ic_check_favorited)
    Drawable icFavorited;

    private boolean isClosed = true;
    private Bitmap photoBitmap;

    private RealmController realmController;
    private Photo photoFullscreen;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_photo);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        unbinder = ButterKnife.bind(this);

        Intent intent = getIntent();
        String photoId = intent.getStringExtra("photoId");
        getPhotoById(photoId);

        realmController = new RealmController();
        if(realmController.isPhotoExists(photoId)){
            floatFavorite.setImageDrawable(icFavorited);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.activity_fullscreen_photo_fab_menu)
    public void onFloatClick(View v) {
        clickFloatMenu();
    }

    @OnClick(R.id.activity_fullscreen_photo_fab_favorite)
    public void onFavorite(View v){
        if(realmController.isPhotoExists(photoFullscreen.getId())){
            realmController.deletePhoto(photoFullscreen);
            floatFavorite.setImageDrawable(icFavorite);
            Toast.makeText(FullscreenPhoto.this,
                                "Favorite Removed",
                                Toast.LENGTH_SHORT)
                    .show();
        } else {
            realmController.savePhoto(photoFullscreen);
            floatFavorite.setImageDrawable(icFavorited);
            Toast.makeText(FullscreenPhoto.this,
                                "Favorited",
                                Toast.LENGTH_SHORT)
                    .show();
        }

        clickFloatMenu();
    }

    @OnClick(R.id.activity_fullscreen_photo_fab_wallpaper)
    public void onWallpaper(View v){
        if(photoBitmap != null)
            if(Functions.setWallpaper(FullscreenPhoto.this, photoBitmap))
                Toast.makeText(FullscreenPhoto.this,
                                "Set Wallpaper Successfuly!",
                                Toast.LENGTH_SHORT)
                        .show();
            else
                Toast.makeText(FullscreenPhoto.this,
                        "Set Wallpaper Failed!",
                        Toast.LENGTH_SHORT)
                        .show();

        clickFloatMenu();
    }

    public void clickFloatMenu(){
        if(isClosed){
            floatFavorite.setVisibility(View.VISIBLE);
            floatWallpaper.setVisibility(View.VISIBLE);
        } else{
            floatFavorite.setVisibility(View.GONE);
            floatWallpaper.setVisibility(View.GONE);
        }

        isClosed = !isClosed;
    }

    public void getPhotoById(String id){
        APIInterface apiInterface = ServiceGenerator.createService(APIInterface.class);
        Call<Photo> getPhoto = apiInterface.getPhoto(id);

        getPhoto.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                if(response.isSuccessful()){
                    photoFullscreen = response.body();

                    username.setText(photoFullscreen.getUser().getUsername());
                    Glide.with(FullscreenPhoto.this)
                            .load(photoFullscreen.getUser().getProfileImage().getSmall())
                            .into(userAvatar);
                    Glide.with(FullscreenPhoto.this)
                            .asBitmap()
                            .load(photoFullscreen.getUrl().getRegular())
                            .centerCrop()
                            .into(new CustomTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    photo.setImageBitmap(resource);
                                    photoBitmap = resource;
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                }
                            });
                } else {
                    Log.d(TAG, "Fail " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                Log.d(TAG, "Fail " + t.getMessage());
            }
        });
    }
}