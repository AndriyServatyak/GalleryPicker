package com.geekapps.andrii.gallerypicker.recycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.recycler.viewholder.ItemSelectedFileViewHolder;
import com.geekapps.andrii.gallerypicker.util.FileOpen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SelectedFileListRecyclerAdapter extends RecyclerView.Adapter<ItemSelectedFileViewHolder> {

    private final List<File> mSelectedFiles;
    private final Context mContext;

    public SelectedFileListRecyclerAdapter(Context context) {
        mSelectedFiles = new ArrayList<>();
        mContext = context;
    }

    public void addFile(List<File> file) {
        int insertedPosition = mSelectedFiles.size();
        mSelectedFiles.addAll(file);
        notifyItemInserted(insertedPosition);
    }

    private void removeFile(int position) {
        mSelectedFiles.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ItemSelectedFileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_selected_file, parent, false);
        return new ItemSelectedFileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemSelectedFileViewHolder holder, int position) {
        File file = mSelectedFiles.get(position);
        holder.bind(file, getListener());
    }

    @Override
    public int getItemCount() {
        return mSelectedFiles.size();
    }

    private SelectedFileClickListener getListener() {
        return new SelectedFileClickListener() {
            @Override
            public void onRemoveClicked(int position) {
                removeFile(position);
            }

            @Override
            public void onOpenClicked(File file) {
                FileOpen.openFile(mContext, file);
            }
        };
    }

    public interface SelectedFileClickListener {
        void onRemoveClicked(int position);

        void onOpenClicked(File file);
    }
}
