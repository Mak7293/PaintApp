package com.soft918.paintapp.data.repository;

import com.soft918.paintapp.data.data_source.PaintUriDao;
import com.soft918.paintapp.data.data_source.PaintUriDatabase;
import com.soft918.paintapp.domain.model.PaintUriEntity;
import com.soft918.paintapp.domain.repository.Repository;

import java.util.List;

import javax.inject.Inject;

public class RepositoryImpl implements Repository {

    private PaintUriDao paintUriDao;

    @Inject
    public RepositoryImpl(PaintUriDao paintUriDao){
        this.paintUriDao = paintUriDao;
    }
    @Override
    public void insertPaintUri(PaintUriEntity paintUriEntity) {
        paintUriDao.insertUriToDb(paintUriEntity);
    }

    @Override
    public void deletePaintUri(PaintUriEntity paintUriEntity) {
        paintUriDao.deleteUriInDb(paintUriEntity);
    }

    @Override
    public List<PaintUriEntity> getAllPaintUri() {
        return paintUriDao.provideUriList();
    }
}
