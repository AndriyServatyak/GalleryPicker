package com.geekapps.andrii.gallerypicker.recycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.activity.gallery.GalleryActivityController;
import com.geekapps.andrii.gallerypicker.model.AlbumModel;
import com.geekapps.andrii.gallerypicker.recycler.viewholder.ItemAlbumViewHolder;

import java.util.ArrayList;
import java.util.List;

public class AlbumRecyclerAdapter extends RecyclerView.Adapter<ItemAlbumViewHolder> {

    private final List<AlbumModel> mAlbumModels;
    private final GalleryActivityController mController;

    public AlbumRecyclerAdapter(GalleryActivityController controller) {
        mAlbumModels = new ArrayList<>();
        mController = controller;
    }

    public void addAll(List<AlbumModel> albumModels) {
        mAlbumModels.clear();
        mAlbumModels.addAll(albumModels);
        notifyDataSetChanged();
    }

    @Override
    public ItemAlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_album, parent, false);
        return new ItemAlbumViewHolder(itemView, mController);
    }

    @Override
    public void onBindViewHolder(ItemAlbumViewHolder holder, int position) {
        holder.bind(mAlbumModels.get(position));
    }

    @Override
    public int getItemCount() {
        return mAlbumModels.size();
    }
}
