package com.soft918.paintapp.domain.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class PaintView extends View {

    public PaintView(Context context, AttributeSet attrs){
        super(context,attrs);
        this.context = context;
        setUpDrawing();

    }
    private Context context;
    private CustomPath mDrawPath;
    private Bitmap mCanvasBitmap;
    private Paint mDrawPaint;
    private Paint mCanvasPaint;
    private float mBrushSize = 0f;
    private int color = Color.BLACK;
    private Canvas canvas;
    private ArrayList<CustomPath> mPath = new ArrayList<CustomPath>();
    private ArrayList<CustomPath> mUndoPath = new ArrayList<CustomPath>();

    private void setUpDrawing() {
        mDrawPaint = new Paint();
        mDrawPath = new CustomPath(color,mBrushSize);
        mDrawPaint.setColor(color);
        mDrawPaint.setStyle(Paint.Style.STROKE);
        mDrawPaint.setStrokeJoin(Paint.Join.ROUND);
        mDrawPaint.setStrokeCap(Paint.Cap.ROUND);
        mCanvasPaint = new Paint(Paint.DITHER_FLAG);
        mBrushSize = 20f;  //no need anymore because setSizeToBrush fun
    }
    public void setPath(ArrayList<CustomPath> list){
        mPath = list;
    }
    public ArrayList<CustomPath> getPath(){
        return mPath;
    }

    public void onClickUndo(){
        if(mPath.size() > 0){
            mUndoPath.add(mPath.remove(mPath.size()-1));
            invalidate();
        }
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCanvasBitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(mCanvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mCanvasBitmap,0f,0f,mCanvasPaint);
        for(int path = 0; path <= mPath.size()-1 ; path++){
            mDrawPaint.setStrokeWidth(mPath.get(path).brushThickness);
            mDrawPaint.setColor(mPath.get(path).color);
            canvas.drawPath(mPath.get(path),mDrawPaint);
        }
        if(!mDrawPath.isEmpty()){
            mDrawPaint.setStrokeWidth(mDrawPath.brushThickness);
            mDrawPaint.setColor(mDrawPath.color);
            canvas.drawPath(mDrawPath,mDrawPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()){
            case  MotionEvent.ACTION_DOWN : {
                mDrawPath.color = color;
                mDrawPath.brushThickness = mBrushSize;
                mDrawPath.reset();
                mDrawPath.moveTo(touchX,touchY);
                break;
            }
            case MotionEvent.ACTION_MOVE : {

                mDrawPath.lineTo(touchX,touchY);
                break;
            }
            case MotionEvent.ACTION_UP : {
                mPath.add(mDrawPath);
                mDrawPath = new CustomPath(color,mBrushSize);
                break;
            }
            default :  return  false;
        }
        invalidate();
        return true;
    }

    public void setSizeForBrush(Float newSize){
        mBrushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize,getResources().getDisplayMetrics());
        mDrawPaint.setStrokeWidth(mBrushSize);   //  BoilerPlate code?
    }

    public void setColor(int newColor){
        color = newColor;
        mDrawPaint.setColor(newColor);  //  BoilerPlate code?
    }

    public static class CustomPath extends Path {
        int color;
        float brushThickness;
        public CustomPath(int color, float brushThickness){
            this.color = color;
            this.brushThickness = brushThickness;
        }
    }
}
