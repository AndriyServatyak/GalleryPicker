package com.geekapps.andrii.gallerypicker.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.geekapps.andrii.gallerypicker.model.MediaModel;

import java.util.ArrayList;
import java.util.List;

public class ImageProvider implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = ImageProvider.class.getSimpleName();
    private static final int URL_LOADER = 0;
    private static final Uri URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private static final String SORT_ORDER = MediaStore.Images.Media.DATA + " DESC";
    private static final String[] PROJECTIONS =
            {MediaStore.Video.Media._ID,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Images.Media.DISPLAY_NAME};
    private final Context mContext;
    private final OnLoadImagesCallback mOnLoadImagesCallback;
    public ImageProvider(Context context, OnLoadImagesCallback onLoadImagesCallback) {
        mContext = context;
        mOnLoadImagesCallback = onLoadImagesCallback;
    }

    public void startLoadImages(LoaderManager loaderManager) {
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
        mOnLoadImagesCallback.onLoadImagesFail();
    }

    private void getImages(Cursor cursor) {
        List<MediaModel> photoModels = new ArrayList<>();
        while (cursor.moveToNext()) {
            String imageName = cursor.getString(cursor.getColumnIndex(PROJECTIONS[3]));
            String imageBucket = cursor.getString(cursor.getColumnIndex(PROJECTIONS[2]));
            String imagePath = cursor.getString(cursor.getColumnIndex(PROJECTIONS[1]));

            Log.v(TAG, imageName + " " + imageBucket);
            MediaModel photoModel = new MediaModel(imageName, imagePath, imageBucket, null);
            photoModels.add(photoModel);
        }

        mOnLoadImagesCallback.onLoadImagesSuccess(photoModels);
    }

    public interface OnLoadImagesCallback {
        void onLoadImagesSuccess(List<MediaModel> photoModels);

        void onLoadImagesFail();
    }
}
