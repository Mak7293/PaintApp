package com.soft918.paintapp.domain.event;

public class Event {
    static public class UpdateColorList extends Event{
        public int listId;
        public UpdateColorList(int listId){
            this.listId = listId;
        }
    }
}
