package com.soft918.paintapp.domain.util;

public class PencilEraser {
    public String state;
    public PencilEraser(String state){
        this.state = state;
    }
    public static PencilEraser pencil = new PencilEraser("pencil");
    public static PencilEraser eraser = new PencilEraser("eraser");
}
