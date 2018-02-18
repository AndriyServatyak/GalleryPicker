package com.geekapps.andrii.gallerypicker.activity.filepicker;

import com.geekapps.andrii.gallerypicker.util.FileProvider;

public class FilePickerPresenter implements FilePickerContract.Presenter {

    private final FilePickerContract.View mView;

    public FilePickerPresenter(FilePickerContract.View view) {
        mView = view;
    }

    @Override
    public void getDirectories(String startPath) {
        mView.onGetDirectoriesSuccess(FileProvider.getFiles(startPath));
    }
}
