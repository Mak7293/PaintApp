package com.soft918.paintapp.domain.repository;

import com.soft918.paintapp.domain.model.PaintUriEntity;

import java.util.List;

public interface Repository {

    abstract void insertPaintUri(PaintUriEntity paintUriEntity);
    abstract void deletePaintUri(PaintUriEntity paintUriEntity);
    abstract List<PaintUriEntity> getAllPaintUri();
}
