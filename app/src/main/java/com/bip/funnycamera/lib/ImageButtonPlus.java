package com.bip.funnycamera.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.ImageButton;

/* renamed from: com.ho.ho.holib.ImageButtonPlus */
public class ImageButtonPlus extends ImageButton {

    /* renamed from: Enabled框 reason: contains not printable characters */
    public boolean f57Enabled;
    private Paint paint;

    public ImageButtonPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.f57Enabled = false;
        this.paint = new Paint();
        this.paint.setColor(-65536);
        this.paint.setStrokeWidth(8.0f);
        this.paint.setStyle(Style.STROKE);
    }

    public ImageButtonPlus(Context context) {
        super(context);
        this.f57Enabled = false;
    }

    public void onDraw(Canvas can) {
        if (this.f57Enabled) {
            Canvas canvas = can;
            canvas.drawRect(0.0f, 0.0f, (float) can.getWidth(), (float) can.getHeight(), this.paint);
        }
    }
}
