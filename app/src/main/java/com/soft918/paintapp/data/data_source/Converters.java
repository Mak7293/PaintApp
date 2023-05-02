package com.soft918.paintapp.data.data_source;

import android.net.Uri;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public String fromUri(Uri uri){
        return uri.getPath();
    }

    @TypeConverter
    public Uri toUri(String path){
        return Uri.parse(path);
    }
}
