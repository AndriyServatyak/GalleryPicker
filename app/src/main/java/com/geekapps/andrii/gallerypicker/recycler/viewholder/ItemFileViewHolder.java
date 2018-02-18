package com.geekapps.andrii.gallerypicker.recycler.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.recycler.adapter.FileListRecyclerAdapter;
import com.geekapps.andrii.gallerypicker.util.TimeUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemFileViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.file_name_tv)
    TextView mFileNameTv;
    @BindView(R.id.file_date_tv)
    TextView mFileDateTv;
    @BindView(R.id.item_file_root)
    View mItemFileRoot;
    @BindView(R.id.select_file_checkbox)
    CheckBox mSelectFileCheckbox;

    public ItemFileViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(File file, FileListRecyclerAdapter.OnItemClickListener listener,
                     boolean isSelected) {
        mSelectFileCheckbox.setChecked(isSelected);
        mFileNameTv.setText(file.getName());
        mFileDateTv.setText(TimeUtils.getFileDate(file.lastModified()));
        mItemFileRoot.setOnClickListener(view -> selectFile(file, listener));
    }

    private void selectFile(File file, FileListRecyclerAdapter.OnItemClickListener listener) {
        boolean isChecked = mSelectFileCheckbox.isChecked();
        listener.onFileClicked(file, !isChecked);
        mSelectFileCheckbox.setChecked(!isChecked);
    }
}
