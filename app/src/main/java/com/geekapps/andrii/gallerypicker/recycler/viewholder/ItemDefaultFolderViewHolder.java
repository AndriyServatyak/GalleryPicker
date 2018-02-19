package com.geekapps.andrii.gallerypicker.recycler.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.geekapps.andrii.gallerypicker.recycler.adapter.FileListRecyclerAdapter;

public class ItemDefaultFolderViewHolder extends RecyclerView.ViewHolder {

    private View mItemView;

    public ItemDefaultFolderViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
    }

    public void bind(FileListRecyclerAdapter.OnItemClickListener listener) {
        mItemView.setOnClickListener(view -> listener.onDefaultFileClicked());
    }
}
