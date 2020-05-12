package com.bip.funnycamera.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;

public class ImageButtonPlus extends androidx.appcompat.widget.AppCompatImageButton {

    public boolean isEnabled;
    private Paint paint;

    public ImageButtonPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isEnabled = false;
        this.paint = new Paint();
        this.paint.setColor(-65536);
        this.paint.setStrokeWidth(8.0f);
        this.paint.setStyle(Style.STROKE);
    }

    public ImageButtonPlus(Context context) {
        super(context);
        this.isEnabled = false;
    }

    public void onDraw(Canvas can) {
        if (this.isEnabled) {
            Canvas canvas = can;
            canvas.drawRect(0.0f, 0.0f, (float) can.getWidth(), (float) can.getHeight(), this.paint);
        }
    }
}
