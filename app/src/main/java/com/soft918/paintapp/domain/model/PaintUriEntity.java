package com.soft918.paintapp.domain.model;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "uri-table")
public class PaintUriEntity {

    public Uri uri;
    @PrimaryKey(autoGenerate = true)
    public int id;

    public PaintUriEntity(int id, Uri uri){
        this.id = id;
        this.uri = uri;
    }
}
