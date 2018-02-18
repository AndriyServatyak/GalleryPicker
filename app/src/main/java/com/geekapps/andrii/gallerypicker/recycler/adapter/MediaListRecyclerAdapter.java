package com.geekapps.andrii.gallerypicker.recycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.activity.gallery.GalleryActivityController;
import com.geekapps.andrii.gallerypicker.model.MediaModel;
import com.geekapps.andrii.gallerypicker.model.eventbus.OnRefreshEvent;
import com.geekapps.andrii.gallerypicker.recycler.viewholder.ItemImageViewHolder;
import com.geekapps.andrii.gallerypicker.recycler.viewholder.ItemVideoViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MediaListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int IMAGE_VIEW_TYPE = 0;
    private static final int VIDEO_VIEW_TYPE = 1;

    private final List<MediaModel> mMediaModels;
    private final GalleryActivityController mController;

    private List<MediaModel> mSelectedItems;

    public MediaListRecyclerAdapter(GalleryActivityController controller) {
        mMediaModels = new ArrayList<>();
        mSelectedItems = new ArrayList<>();
        mController = controller;
    }

    public void addAll(List<MediaModel> mediaModels) {
        mMediaModels.clear();
        mMediaModels.addAll(mediaModels);
        notifyDataSetChanged();
    }

    private void refreshSelectedItemView() {
        EventBus.getDefault().post(new OnRefreshEvent(mSelectedItems));
    }

    private void updateSelectedItems(MediaModel mediaModel, boolean isSelected) {
        if (isSelected) {
            mSelectedItems.add(mediaModel);
        } else {
            mSelectedItems.remove(mediaModel);
        }
        refreshSelectedItemView();
    }

    public List<MediaModel> getSelectedItems() {
        return mSelectedItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case IMAGE_VIEW_TYPE: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_image, parent, false);
                return new ItemImageViewHolder(itemView);
            }
            case VIDEO_VIEW_TYPE: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_video, parent, false);
                return new ItemVideoViewHolder(itemView);
            }
        }
        throw new UnsupportedOperationException("Incorrect view type");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MediaModel mediaModel = mMediaModels.get(position);
        if (holder instanceof ItemImageViewHolder) {
            ((ItemImageViewHolder) holder).bind(mediaModel,
                    getOnItemClickedListener());
        } else if (holder instanceof ItemVideoViewHolder)
            ((ItemVideoViewHolder) holder).bind(mediaModel,
                    getOnItemClickedListener());
    }

    private OnItemClickedListener getOnItemClickedListener() {
        return new OnItemClickedListener() {

            @Override
            public void onPhotoClicked(int position) {
                mController.goToPhotoFullScreen(mMediaModels, position);
            }

            @Override
            public void onImageSelected(int position, boolean isSelected) {
                updateSelectedItems(mMediaModels.get(position), isSelected);
            }

            @Override
            public void onVideoClicked(MediaModel mediaModel) {
                mController.goToVideoPlayer(mediaModel);
            }
        };
    }

    @Override
    public int getItemViewType(int position) {
        MediaModel mediaModel = mMediaModels.get(position);
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
        return mMediaModels.size();
    }

    public interface OnItemClickedListener {

        void onPhotoClicked(int position);

        void onImageSelected(int position, boolean isSelected);

        void onVideoClicked(MediaModel mediaModel);
    }
}
