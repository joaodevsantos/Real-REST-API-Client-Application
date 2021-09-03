package com.example.wallpaper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wallpaper.Models.Collection;
import com.example.wallpaper.Models.Photo;
import com.example.wallpaper.R;
import com.example.wallpaper.Utils.SquareImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionsAdapter extends BaseAdapter {

    private Context context;
    private List<Collection> collections;

    public CollectionsAdapter(Context context, List<Collection> collections){
        this.context = context;
        this.collections = collections;
    }

    @Override
    public int getCount() {
        return collections.size();
    }

    @Override
    public Object getItem(int position) {
        return collections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return collections.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(context)
                                            .inflate(R.layout.item_collection, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ButterKnife.bind(this, convertView);
        Collection collection = collections.get(position);

        if(collection.getTitle() != null)
            holder.title.setText(collection.getTitle());

        holder.totalPhotos.setText(collection.getTotalPhotos());

        Glide
            .with(context)
            .load(collection.getCoverPhoto().getUrl().getRegular())
            .into(holder.photo);

        return null;
    }

    static class ViewHolder {
        @BindView(R.id.item_collection_photo)
        SquareImageView photo;
        @BindView(R.id.item_collection_title)
        TextView title;
        @BindView(R.id.item_collection_total_photos)
        TextView totalPhotos;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
