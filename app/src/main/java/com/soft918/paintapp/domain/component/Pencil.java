package com.soft918.paintapp.domain.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.soft918.paintapp.R;

public class Pencil extends View {

    Paint mainPaint;
    Paint linePaint;
    Paint pencilTipPaint;
    int color;
    float length;
    Context context;

    public Pencil(Context context, int color,float length){
        super(context);
        this.mainPaint = new Paint();
        this.color = color;
        this.length = length;
        this.context = context;
        mainPaint.setColor(this.color);
        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(pxFromDp(context,2f));
        linePaint.setStyle(Paint.Style.STROKE);
        pencilTipPaint = new Paint();
        pencilTipPaint.setColor(ContextCompat.getColor(context, R.color.pencil_tip));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerWidth = (float)getWidth()/2;

        RectF rect = new RectF(centerWidth-40f,0f,centerWidth+40f,length);
        canvas.drawRect(rect, mainPaint);
        canvas.drawLine(centerWidth-40f,0f,centerWidth-40f,length,linePaint);
        canvas.drawLine(centerWidth-20f,0f,centerWidth-20f,length,linePaint);
        canvas.drawLine(centerWidth+40f,0f,centerWidth+40f,length,linePaint);
        canvas.drawLine(centerWidth+20f,0f,centerWidth+20f,length,linePaint);
        Path pathTip = new Path();
        pathTip.moveTo(centerWidth-43f,length);
        pathTip.lineTo(centerWidth,length+90f);
        pathTip.lineTo(centerWidth+43f,length);
        pathTip.close();
        canvas.drawPath(pathTip,pencilTipPaint);

        Path pathTipColor = new Path();
        pathTipColor.moveTo(centerWidth+1,length+90f);
        pathTipColor.lineTo(centerWidth-19,length+45f);
        pathTipColor.lineTo(centerWidth+19,length+45f);
        pathTipColor.close();
        canvas.drawPath(pathTipColor,mainPaint);

        canvas.drawLine(centerWidth+43f,length,centerWidth-43f,length,linePaint);
        canvas.drawLine(centerWidth-41f,length,centerWidth+1,length+90f,linePaint);
        canvas.drawLine(centerWidth+41f,length,centerWidth-1,length+90f,linePaint);

        canvas.drawLine(centerWidth-19,length+45f,centerWidth+19,length+45f,linePaint);
    }
    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
