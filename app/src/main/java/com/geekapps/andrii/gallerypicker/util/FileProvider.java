package com.geekapps.andrii.gallerypicker.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileProvider {
    public static List<File> getFiles(String path) {
        File file = new File(path);
        return Arrays.asList(file.listFiles());
    }
}
