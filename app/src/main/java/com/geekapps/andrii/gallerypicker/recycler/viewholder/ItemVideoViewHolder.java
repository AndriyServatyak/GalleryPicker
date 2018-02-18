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

public class ItemVideoViewHolder extends RecyclerView.ViewHolder {

    @BindDrawable(R.drawable.ic_select_empty)
    Drawable mNotSelectedDrawable;
    @BindDrawable(R.drawable.ic_check_circle)
    Drawable mSelectedDrawable;

    @BindView(R.id.item_video_preview)
    ImageView mVideoPreviewIv;
    @BindView(R.id.select_indicator_iv)
    ImageView mSelectIndicatorIv;

    public ItemVideoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(MediaModel mediaModel,
                     MediaListRecyclerAdapter.OnItemClickedListener onItemClickedListener) {
        mVideoPreviewIv.setImageResource(R.drawable.ic_photo);
        Utils.loadImage(mediaModel, mVideoPreviewIv);
        mSelectIndicatorIv.setOnClickListener(view ->
                selectOrDeselectImage(mediaModel, onItemClickedListener));
        mVideoPreviewIv.setOnClickListener(view ->
                onItemClickedListener.onVideoClicked(mediaModel));
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
