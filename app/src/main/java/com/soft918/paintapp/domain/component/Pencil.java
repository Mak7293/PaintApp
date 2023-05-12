package com.soft918.paintapp.domain.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.soft918.paintapp.R;

public class Pencil extends View {

    private Paint mainPaint;
    private Paint linePaint;
    private Paint pencilTipPaint;
    private int color;
    private float length;
    private Context context;

    public Pencil(Context context, int color,float length){
        super(context);
        this.mainPaint = new Paint();
        this.color = color;
        this.length = length;
        this.context = context;
        mainPaint.setColor(this.color);
        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(toDp_toPx(context,3f));
        linePaint.setStyle(Paint.Style.STROKE);
        pencilTipPaint = new Paint();
        pencilTipPaint.setColor(ContextCompat.getColor(context, R.color.pencil_tip));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerWidth = (float)getWidth()/2;

        RectF rect = new RectF(
                toDp_toPx(context, centerWidth-34f),
                toDp_toPx(context, 0f),
                toDp_toPx(context, centerWidth+34f),
                toDp_toPx(context, length)
        );
        canvas.drawRect(rect, mainPaint);
        canvas.drawLine(
                toDp_toPx(context, centerWidth-34f),
                toDp_toPx(context, 0f),
                toDp_toPx(context, centerWidth-34f),
                toDp_toPx(context, length),
                linePaint
        );
        canvas.drawLine(
                toDp_toPx(context, centerWidth-17f),
                toDp_toPx(context, 0f),
                toDp_toPx(context, centerWidth-17f),
                toDp_toPx(context, length),
                linePaint
        );
        canvas.drawLine(
                toDp_toPx(context, centerWidth+34f),
                toDp_toPx(context, 0f),
                toDp_toPx(context, centerWidth+34f),
                toDp_toPx(context, length),
                linePaint
        );
        canvas.drawLine(
                toDp_toPx(context, centerWidth+17f),
                toDp_toPx(context, 0f),
                toDp_toPx(context, centerWidth+17f),
                toDp_toPx(context, length),
                linePaint
        );
        Path pathTip = new Path();
        pathTip.moveTo(
                toDp_toPx(context, centerWidth-34f),
                toDp_toPx(context, length)
        );
        pathTip.lineTo(
                toDp_toPx(context, centerWidth),
                toDp_toPx(context, length+75f)
        );
        pathTip.lineTo(
                toDp_toPx(context, centerWidth+34f),
                toDp_toPx(context, length)
        );
        pathTip.close();
        canvas.drawPath(pathTip,pencilTipPaint);

        Path pathTipColor = new Path();
        pathTipColor.moveTo(
                toDp_toPx(context, centerWidth+1),
                toDp_toPx(context, length+75f)
        );
        pathTipColor.lineTo(
                toDp_toPx(context, centerWidth-16),
                toDp_toPx(context, length+39f)
        );
        pathTipColor.lineTo(
                toDp_toPx(context, centerWidth+16),
                toDp_toPx(context, length+39f)
        );
        pathTipColor.close();
        canvas.drawPath(pathTipColor,mainPaint);

        canvas.drawLine(
                toDp_toPx(context, centerWidth+34f),
                toDp_toPx(context, length),
                toDp_toPx(context, centerWidth-34f),
                toDp_toPx(context, length),
                linePaint
        );
        canvas.drawLine(
                toDp_toPx(context, centerWidth-34f),
                toDp_toPx(context, length),
                toDp_toPx(context, centerWidth),
                toDp_toPx(context, length+75f),
                linePaint
        );
        canvas.drawLine(
                toDp_toPx(context, centerWidth+34f),
                toDp_toPx(context, length),
                toDp_toPx(context, centerWidth),
                toDp_toPx(context, length+75f),
                linePaint
        );
        canvas.drawLine(
                toDp_toPx(context, centerWidth-16),
                toDp_toPx(context, length+39f),
                toDp_toPx(context, centerWidth+16),
                toDp_toPx(context, length+39f),
                linePaint
        );
    }
    private static float toPx(final Context context, final float _float) {
        return _float * context.getResources().getDisplayMetrics().density;
    }
    private static float toDp(final Context context, final float _float) {
        return _float / context.getResources().getDisplayMetrics().density;
    }
    private static float toDp_toPx(final Context context, final float _float) {
        float toDp = toDp(context,_float);
        return toPx(context,toDp);
    }
}
