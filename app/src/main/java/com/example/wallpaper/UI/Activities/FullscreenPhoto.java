package com.example.wallpaper.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class FullscreenPhoto extends AppCompatActivity {

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
    }
}