package com.geekapps.andrii.gallerypicker.recycler.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.recycler.adapter.SelectedFileListRecyclerAdapter;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemSelectedFileViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.file_name_tv)
    TextView mFileNameTv;
    @BindView(R.id.delete_action_iv)
    ImageView mDeleteActionIv;
    @BindView(R.id.item_file_root)
    View mItemFileRoot;

    public ItemSelectedFileViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(File file, SelectedFileListRecyclerAdapter.SelectedFileClickListener listener) {
        mFileNameTv.setText(file.getName());
        mDeleteActionIv.setOnClickListener(view -> listener.onRemoveClicked(getAdapterPosition()));
        mItemFileRoot.setOnClickListener(view -> listener.onOpenClicked(file));
    }
}
