package com.geekapps.andrii.gallerypicker.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.geekapps.andrii.gallerypicker.model.MediaModel;

import java.util.ArrayList;
import java.util.List;

public class VideoProvider implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int URL_LOADER = 0;
    private static final Uri URI = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    private static final String SORT_ORDER = MediaStore.Video.Media.DATA + " DESC";
    private final Context mContext;
    private final OnLoadVideoCallback mOnLoadVideoCallback;
    private String[] PROJECTIONS = {
            MediaStore.MediaColumns.DATA,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Thumbnails.DATA};
    public VideoProvider(Context context, OnLoadVideoCallback onLoadVideoCallback) {
        mContext = context;
        mOnLoadVideoCallback = onLoadVideoCallback;
    }

    public void startLoadVideos(LoaderManager loaderManager) {
        loaderManager.initLoader(URL_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(mContext,
                URI,
                PROJECTIONS,
                null,
                null,
                SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        getImages(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mOnLoadVideoCallback.onLoadVideoFail();
    }

    private void getImages(Cursor cursor) {
        List<MediaModel> videoModels = new ArrayList<>();
        while (cursor.moveToNext()) {
            String videoPath = cursor.getString(cursor.getColumnIndex(PROJECTIONS[0]));
            String videoBucket = cursor.getString(cursor.getColumnIndex(PROJECTIONS[1]));
            String videoName = cursor.getString(cursor.getColumnIndex(PROJECTIONS[2]));
            String videoThumb = cursor.getString(cursor.getColumnIndex(PROJECTIONS[4]));

            videoModels.add(new MediaModel(videoName, videoPath, videoBucket, videoThumb));
        }

        mOnLoadVideoCallback.onLoadVideoSuccess(videoModels);
    }

    public interface OnLoadVideoCallback {
        void onLoadVideoSuccess(List<MediaModel> videoModels);

        void onLoadVideoFail();
    }
}
