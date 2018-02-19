package com.geekapps.andrii.gallerypicker.recycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.model.DefaultFile;
import com.geekapps.andrii.gallerypicker.model.eventbus.OnDefaultFolderClickedEvent;
import com.geekapps.andrii.gallerypicker.model.eventbus.OnFileSelectEvent;
import com.geekapps.andrii.gallerypicker.model.eventbus.OnFolderClickedEvent;
import com.geekapps.andrii.gallerypicker.recycler.viewholder.ItemDefaultFolderViewHolder;
import com.geekapps.andrii.gallerypicker.recycler.viewholder.ItemFileViewHolder;
import com.geekapps.andrii.gallerypicker.recycler.viewholder.ItemFolderViewHolder;
import com.geekapps.andrii.gallerypicker.util.Utils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int FOLDER_VIEW_TYPE = 0;
    private static final int FILE_VIEW_TYPE = 1;
    private static final int DEFAULT_FOLDER_TYPE = 2;

    private final List<File> mFiles;
    private final List<File> mPreviousClickedFolders;
    private final List<File> mSelectedFiles;

    public FileListRecyclerAdapter() {
        mFiles = new ArrayList<>();
        mPreviousClickedFolders = new ArrayList<>();
        mSelectedFiles = new ArrayList<>();
    }

    public void addAll(List<File> files) {
        mFiles.clear();
        mFiles.addAll(files);
        mFiles.add(0, new DefaultFile(""));
        notifyDataSetChanged();
    }

    public void removeFolder(File file) {
        mPreviousClickedFolders.remove(file);
    }

    private void addSelectedFolder(File file) {
        mPreviousClickedFolders.add(0, file);
    }

    public File getPreviousSelectedFolder() {
        return Utils.isEmpty(mPreviousClickedFolders) ? null : mPreviousClickedFolders.get(0);
    }

    public boolean isLastFolder() {
        return mPreviousClickedFolders.size() == 0;
    }

    public List<File> getSelectedFiles() {
        return mSelectedFiles;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case FOLDER_VIEW_TYPE: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_directory, parent, false);
                return new ItemFolderViewHolder(itemView);
            }
            case FILE_VIEW_TYPE: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_file, parent, false);
                return new ItemFileViewHolder(itemView);
            }
            case DEFAULT_FOLDER_TYPE:
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_default_directory, parent, false);
                return new ItemDefaultFolderViewHolder(itemView);

        }
        throw new NullPointerException("View type doesn't exist");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        File file = mFiles.get(position);
        if (holder instanceof ItemFolderViewHolder) {
            ((ItemFolderViewHolder) holder).bind(file, getListener());
        } else if (holder instanceof ItemFileViewHolder) {
            ((ItemFileViewHolder) holder).bind(file, getListener(), isSelected(file));
        } else if (holder instanceof ItemDefaultFolderViewHolder) {
            ((ItemDefaultFolderViewHolder) holder).bind(getListener());
        }
    }

    private boolean isSelected(File file) {
        return mSelectedFiles.contains(file);
    }

    @Override
    public int getItemViewType(int position) {
        File file = mFiles.get(position);
        if (file instanceof DefaultFile) {
            return DEFAULT_FOLDER_TYPE;
        } else if (file.isDirectory()) {
            return FOLDER_VIEW_TYPE;
        } else {
            return FILE_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    private OnItemClickListener getListener() {
        return new OnItemClickListener() {
            @Override
            public void onFolderClicked(File file) {
                addSelectedFolder(file.getParentFile());
                EventBus.getDefault().post(new OnFolderClickedEvent(file));
            }

            @Override
            public void onFileClicked(File file, boolean isSelected) {
                addOrRemoveFile(file, isSelected);
            }

            @Override
            public void onDefaultFileClicked() {
                EventBus.getDefault().post(new OnDefaultFolderClickedEvent());
            }
        };
    }

    private void addOrRemoveFile(File file, boolean isSelected) {
        if (isSelected) {
            mSelectedFiles.add(file);
        } else {
            mSelectedFiles.remove(file);
        }
        EventBus.getDefault().post(new OnFileSelectEvent());
    }

    public interface OnItemClickListener {
        void onFolderClicked(File file);

        void onFileClicked(File file, boolean isSelected);

        void onDefaultFileClicked();
    }
}
