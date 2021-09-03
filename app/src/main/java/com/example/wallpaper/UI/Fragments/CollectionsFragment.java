package com.example.wallpaper.UI.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wallpaper.Adapters.CollectionsAdapter;
import com.example.wallpaper.Models.Collection;
import com.example.wallpaper.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CollectionsFragment extends Fragment {

    private final String TAG = CollectionsFragment.class.getSimpleName();

    @BindView(R.id.fragment_collections_progressBar)
    private ProgressBar progressBar;
    @BindView(R.id.fragment_collections_gridView)
    private GridView gridView;

    private CollectionsAdapter collectionsAdapter;
    private List<Collection> collectionList = new ArrayList<>();

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_collections, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
