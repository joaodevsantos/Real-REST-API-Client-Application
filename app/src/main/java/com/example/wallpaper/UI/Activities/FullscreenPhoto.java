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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.wallpaper.Models.Photo;
import com.example.wallpaper.R;
import com.example.wallpaper.WebService.APIInterface;
import com.example.wallpaper.WebService.ServiceGenerator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    private boolean isClosed = true;

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
        clickFloatMenu();
    }

    @OnClick(R.id.activity_fullscreen_photo_fab_wallpaper)
    public void onWallpaper(View v){
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
                    Photo pic = response.body();

                    username.setText(pic.getUser().getUsername());
                    Glide.with(FullscreenPhoto.this)
                            .load(pic.getUser().getProfileImage().getSmall())
                            .into(userAvatar);
                    Glide.with(FullscreenPhoto.this)
                            .asBitmap()
                            .load(pic.getUrl().getRegular())
                            .centerCrop()
                            .into(new CustomTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    photo.setImageBitmap(resource);
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                }
                            });
                } else {

                }
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                Log.d(TAG, "Fail " + t.getMessage());
            }
        });
    }
}