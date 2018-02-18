package com.geekapps.andrii.gallerypicker.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    private static SimpleDateFormat fileDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
            Locale.getDefault());

    public static String getFileDate(long time) {
        Date date = new Date(time);
        return String.valueOf(new StringBuilder(fileDateFormat.format(date)));
    }
}
