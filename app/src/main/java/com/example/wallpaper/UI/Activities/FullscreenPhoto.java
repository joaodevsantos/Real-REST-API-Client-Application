package com.example.wallpaper.UI.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wallpaper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.OnClick;

public class FullscreenPhoto extends AppCompatActivity {

    @BindView(R.id.activity_fullscreen_photo_fab_menu)
    FloatingActionButton floatMenu;
    @BindView(R.id.activity_fullscreen_photo_fab_favorite)
    FloatingActionButton floatFavorite;
    @BindView(R.id.activity_fullscreen_photo_fab_wallpaper)
    FloatingActionButton floatWallpaper;

    private boolean isClosed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.activity_fullscreen_photo_fab_menu)
    public void onFloatClick(View v) {
        if(isClosed){
            floatFavorite.setVisibility(View.VISIBLE);
            floatWallpaper.setVisibility(View.VISIBLE);
        } else{
            floatFavorite.setVisibility(View.GONE);
            floatWallpaper.setVisibility(View.GONE);
        }

        isClosed = !isClosed;
    }
}