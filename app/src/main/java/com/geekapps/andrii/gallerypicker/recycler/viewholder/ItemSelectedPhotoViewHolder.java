package com.geekapps.andrii.gallerypicker.recycler.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.model.MediaModel;
import com.geekapps.andrii.gallerypicker.recycler.adapter.SelectedMediaListRecyclerAdapter;
import com.geekapps.andrii.gallerypicker.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemSelectedPhotoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.selected_photo_iv)
    ImageView mSelectedPhotoIv;
    @BindView(R.id.delete_selected_iv)
    ImageView mDeleteSelectedIv;

    public ItemSelectedPhotoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(MediaModel mediaModel, SelectedMediaListRecyclerAdapter.SelectedItemClickListener listener) {
        Utils.loadImage(mediaModel, mSelectedPhotoIv);
        mDeleteSelectedIv.setOnClickListener(view -> listener.onRemoveClicked(getAdapterPosition()));
        mSelectedPhotoIv.setOnClickListener(view -> listener.onItemPhotoClicked(mediaModel));
    }
}
