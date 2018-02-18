package com.geekapps.andrii.gallerypicker.recycler.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.recycler.adapter.FileListRecyclerAdapter;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemFolderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.folder_name_tv)
    TextView mFolderNameTv;
    @BindView(R.id.item_folder_root)
    View mItemFolderRoot;

    public ItemFolderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(File file, FileListRecyclerAdapter.OnItemClickListener listener) {
        mFolderNameTv.setText(file.getName());
        mItemFolderRoot.setOnClickListener(view -> listener.onFolderClicked(file));
    }
}
