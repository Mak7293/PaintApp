package com.soft918.paintapp.domain.event;

import android.graphics.Bitmap;

import com.soft918.paintapp.domain.model.PaintUriEntity;

public class Event {
    static public class UpdateColorList extends Event{
        public int listId;
        public UpdateColorList(int listId){
            this.listId = listId;
        }
    }
    static public class SelectPencilOrEraser extends Event{
        public String state;
        public SelectPencilOrEraser(String state){
            this.state = state;
        }
    }
    static public class changeSize extends Event{
        public String size;
        public String state;
        public changeSize(String size,String state){
            this.size = size;
            this.state = state;
        }
    }
    static public class saveBitmapInDeviceStorage extends Event{
        public Bitmap bitmap;
        public saveBitmapInDeviceStorage(Bitmap bitmap){
            this.bitmap = bitmap;
        }
    }
    static public class deletePaintInDb extends Event{
        public PaintUriEntity paintUriEntity;
        public deletePaintInDb(PaintUriEntity paintUriEntity){
            this.paintUriEntity = paintUriEntity;
        }
    }
    /*static public class sharePaintPicture extends Event{
        public PaintUriEntity paintUriEntity;
        public sharePaintPicture(PaintUriEntity paintUriEntity){
            this.paintUriEntity = paintUriEntity;
        }
    }*/
}
