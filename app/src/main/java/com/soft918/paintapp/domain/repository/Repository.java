package com.soft918.paintapp.domain.repository;

import androidx.lifecycle.LiveData;

import com.soft918.paintapp.domain.model.PaintUriEntity;

import java.util.List;

public interface Repository {

    abstract void insertPaintUri(PaintUriEntity paintUriEntity);
    abstract void deletePaintUri(PaintUriEntity paintUriEntity);
    abstract LiveData<List<PaintUriEntity>> getAllPaintUri();
}
