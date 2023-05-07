package com.soft918.paintapp.domain.model;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "uri-table")
public class PaintUriEntity {

    public String contentUri;
    public String fileUri;
    @PrimaryKey(autoGenerate = true)
    public int id;

    public PaintUriEntity(int id, String contentUri,String fileUri){
        this.id = id;
        this.contentUri = contentUri;
        this.fileUri = fileUri;
    }
}
