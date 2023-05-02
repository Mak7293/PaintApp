package com.soft918.paintapp.data.data_source;

import android.net.Uri;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.soft918.paintapp.domain.model.PaintUriEntity;

import java.util.List;

@Dao
public interface PaintUriDao {

    @Insert
    public void insertUriToDb(PaintUriEntity uri);

    @Delete
    public void deleteUriInDb(PaintUriEntity uri);

    @Query("SELECT * FROM `uri-table`")
    public List<PaintUriEntity> provideUriList();
}
