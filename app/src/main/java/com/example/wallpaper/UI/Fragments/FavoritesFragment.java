package com.example.wallpaper.UI.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallpaper.Adapters.PhotosAdapter;
import com.example.wallpaper.Models.Photo;
import com.example.wallpaper.R;
import com.example.wallpaper.Utils.RealmController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;

public class FavoritesFragment extends Fragment {

    @BindView(R.id.fragment_favorite_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_favorite_notification)
    TextView notification;

    private RealmController realm;

    private PhotosAdapter photosAdapter;
    private List<Photo> photos = new ArrayList<>();

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        unbinder = ButterKnife.bind(this, view);

        // Recycler View
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        photosAdapter = new PhotosAdapter(getContext(), photos);
        recyclerView.setAdapter(photosAdapter);

        // Get the Photos
        realm = new RealmController();
        photos.addAll(realm.getPhotos());

        if(photos.size() == 0){
            notification.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else
            photosAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
