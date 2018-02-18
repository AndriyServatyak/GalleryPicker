package com.geekapps.andrii.gallerypicker.util;

import android.widget.ImageView;

import com.geekapps.andrii.gallerypicker.model.MediaModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Utils {

    public static boolean isEmpty(List objectList) {
        return objectList == null || objectList.isEmpty();
    }

    public static void loadImage(MediaModel mediaModel, ImageView imageView) {
        Observable.fromCallable(mediaModel::getBitmap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imageView::setImageBitmap);
    }
}
