package com.geekapps.andrii.gallerypicker.activity.filepicker;

import java.io.File;
import java.util.List;

public interface FilePickerContract {

    interface View {
        void onGetDirectoriesSuccess(List<File> files);

        void onGetDirectoriesFail();
    }

    interface Presenter {
        void getDirectories(String startPath);
    }
}
