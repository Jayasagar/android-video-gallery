package com.dubsmash.assignment.gallery;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.dubsmash.assignment.gallery.GalleryApp.TAG;

public class DateUtils {

    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(GalleryApp.DATE_PATTERN);
        return dateFormat.format(date);
    }

    public static Date formatMediaDate(String date) {
        try {
            Date inputDate = new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.getDefault()).parse(date);
            return inputDate;
        } catch (Exception e){
            Log.w(TAG, "error parsing date: ", e);
        }
        return new Date();
    }
}
