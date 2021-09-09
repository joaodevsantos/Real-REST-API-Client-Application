package com.example.wallpaper.UI.Fragments;

import android.os.Bundle;
import android.util.Log;
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
import com.example.wallpaper.Utils.Functions;
import com.example.wallpaper.WebService.APIInterface;
import com.example.wallpaper.WebService.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionsFragment extends Fragment {

    private final String TAG = CollectionsFragment.class.getSimpleName();

    @BindView(R.id.fragment_collections_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.fragment_collections_gridView)
    GridView gridView;

    private CollectionsAdapter collectionsAdapter;
    private List<Collection> collectionList = new ArrayList<>();

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_collections, container, false);
        unbinder = ButterKnife.bind(this, view);

        collectionsAdapter = new CollectionsAdapter(getActivity(), collectionList);
        gridView.setAdapter(collectionsAdapter);

        showProgressBar(true);
        getCollections();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnItemClick(R.id.fragment_collections_gridView)
    public void setGridView(int position){
        Collection collection = collectionList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("collectionId", collection.getId());

        CollectionFragment collectionFragment = new CollectionFragment();
        collectionFragment.setArguments(bundle);

        Functions.changeMainFragmentWithBack(getActivity(), collectionFragment);
    }

    private void getCollections() {
        APIInterface apiInterface = ServiceGenerator.createService(APIInterface.class);
        Call<List<Collection>> call = apiInterface.getCollections();

        call.enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if(response.isSuccessful()){
                    collectionList.addAll(response.body());
                    collectionsAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Fail " + response.message());
                }
                showProgressBar(false);
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                Log.e(TAG, "Fail " + t.getMessage());
                showProgressBar(false);
            }
        });
    }

    private void showProgressBar(boolean isShow){
        if(isShow){
            progressBar.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
        }
    }
}
