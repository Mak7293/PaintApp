package com.soft918.paintapp.domain.event;

import android.graphics.Bitmap;

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
}
