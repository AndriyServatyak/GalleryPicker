package com.geekapps.andrii.gallerypicker.model;

import android.support.annotation.NonNull;

import java.io.File;
import java.net.URI;

public class DefaultFile extends File {

    public DefaultFile(@NonNull String pathname) {
        super(pathname);
    }

    public DefaultFile(String parent, @NonNull String child) {
        super(parent, child);
    }

    public DefaultFile(File parent, @NonNull String child) {
        super(parent, child);
    }

    public DefaultFile(@NonNull URI uri) {
        super(uri);
    }
}
