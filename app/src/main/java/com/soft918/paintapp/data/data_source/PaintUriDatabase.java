package com.soft918.paintapp.data.data_source;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.soft918.paintapp.domain.model.PaintUriEntity;

@Database(
        entities = PaintUriEntity.class,
        version = 1,
        exportSchema = false
)
@TypeConverters(Converters.class)
public abstract class PaintUriDatabase extends RoomDatabase {

    abstract public PaintUriDao paintUriDao();

    public final static String DATABASE_NAME = "paint_db";
}
