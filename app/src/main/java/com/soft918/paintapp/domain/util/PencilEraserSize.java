package com.soft918.paintapp.domain.util;


public class PencilEraserSize {

    public String size;
    public PencilEraserSize(String size){
        this.size = size;
    }
    public static PencilEraserSize smallSize = new PencilEraserSize("small");
    public static PencilEraserSize mediumSize = new PencilEraserSize("medium");
    public static PencilEraserSize largeSize = new PencilEraserSize("large");
    public static PencilEraserSize extraLargeSize = new PencilEraserSize("extraLarge");
}
