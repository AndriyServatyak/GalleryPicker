package com.geekapps.andrii.gallerypicker.recycler.viewholder;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.model.MediaModel;
import com.geekapps.andrii.gallerypicker.recycler.adapter.MediaListRecyclerAdapter;
import com.geekapps.andrii.gallerypicker.util.Utils;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemImageViewHolder extends RecyclerView.ViewHolder {

    @BindDrawable(R.drawable.ic_select_empty)
    Drawable mNotSelectedDrawable;
    @BindDrawable(R.drawable.ic_check_circle)
    Drawable mSelectedDrawable;

    @BindView(R.id.media_image_root)
    ImageView mMediaImageIv;
    @BindView(R.id.select_indicator_iv)
    ImageView mSelectIndicatorIv;

    public ItemImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(MediaModel mediaModel,
                     MediaListRecyclerAdapter.OnItemClickedListener onItemClickedListener) {
        mMediaImageIv.setImageResource(R.drawable.ic_photo);
        Utils.loadImage(mediaModel, mMediaImageIv);
        mSelectIndicatorIv.setOnClickListener(view ->
                selectOrDeselectImage(mediaModel, onItemClickedListener));
        mMediaImageIv.setOnClickListener(view ->
                onItemClickedListener.onPhotoClicked(getAdapterPosition()));
    }

    private void initBackground(boolean isSelected) {
        mSelectIndicatorIv.setImageDrawable(isSelected ? mSelectedDrawable : mNotSelectedDrawable);
    }

    private void selectOrDeselectImage(MediaModel mediaModel,
                                       MediaListRecyclerAdapter.OnItemClickedListener onItemClickedListener) {
        boolean reverseSelectedValue = !mediaModel.isSelected();
        onItemClickedListener.onImageSelected(getAdapterPosition(), reverseSelectedValue);
        mediaModel.setSelected(reverseSelectedValue);
        initBackground(reverseSelectedValue);
    }
}
