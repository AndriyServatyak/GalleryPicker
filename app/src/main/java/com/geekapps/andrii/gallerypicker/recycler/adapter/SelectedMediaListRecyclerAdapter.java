package com.geekapps.andrii.gallerypicker.recycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.model.MediaModel;
import com.geekapps.andrii.gallerypicker.model.eventbus.OnShowImageEvent;
import com.geekapps.andrii.gallerypicker.model.eventbus.OnShowVideoEvent;
import com.geekapps.andrii.gallerypicker.recycler.viewholder.ItemSelectedPhotoViewHolder;
import com.geekapps.andrii.gallerypicker.recycler.viewholder.ItemSelectedVideoViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class SelectedMediaListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int IMAGE_VIEW_TYPE = 0;
    private static final int VIDEO_VIEW_TYPE = 1;

    private final List<MediaModel> mSelectedItems;

    public SelectedMediaListRecyclerAdapter() {
        mSelectedItems = new ArrayList<>();
    }

    public void add(List<MediaModel> mediaModels) {
        int insertPosition = mSelectedItems.size();
        mSelectedItems.addAll(mediaModels);
        notifyItemInserted(insertPosition);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case IMAGE_VIEW_TYPE: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_selected_image, parent, false);
                return new ItemSelectedPhotoViewHolder(itemView);
            }
            case VIDEO_VIEW_TYPE: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_selected_video, parent, false);
                return new ItemSelectedVideoViewHolder(itemView);
            }
        }
        throw new UnsupportedOperationException("Incorrect view type");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MediaModel mediaModel = mSelectedItems.get(position);
        if (holder instanceof ItemSelectedPhotoViewHolder) {
            ((ItemSelectedPhotoViewHolder) holder).bind(mediaModel, getListener());
        } else if (holder instanceof ItemSelectedVideoViewHolder)
            ((ItemSelectedVideoViewHolder) holder).bind(mediaModel, getListener());
    }

    private SelectedItemClickListener getListener() {
        return new SelectedItemClickListener() {
            @Override
            public void onItemPhotoClicked(MediaModel mediaModel) {
                EventBus.getDefault().post(new OnShowImageEvent(mSelectedItems,
                        mSelectedItems.indexOf(mediaModel)));
            }

            @Override
            public void onItemVideoClicked(MediaModel mediaModel) {
                EventBus.getDefault().post(new OnShowVideoEvent(mediaModel));
            }

            @Override
            public void onRemoveClicked(int position) {
                mSelectedItems.remove(position);
                notifyItemRemoved(position);
            }
        };
    }

    @Override
    public int getItemViewType(int position) {
        MediaModel mediaModel = mSelectedItems.get(position);
        switch (mediaModel.getMediaType()) {
            case IMAGE:
                return IMAGE_VIEW_TYPE;
            case VIDEO:
                return VIDEO_VIEW_TYPE;
        }
        throw new UnsupportedOperationException("View type not found");
    }

    @Override
    public int getItemCount() {
        return mSelectedItems.size();
    }

    public interface SelectedItemClickListener {
        void onItemPhotoClicked(MediaModel mediaModel);

        void onItemVideoClicked(MediaModel mediaModel);

        void onRemoveClicked(int position);
    }
}
