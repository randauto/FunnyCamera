package com.bip.funnycamera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.hardware.Camera.Size;
import android.opengl.GLES20;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.bip.funnycamera.lib.Camera;
import com.bip.funnycamera.lib.Cursor;
import com.bip.funnycamera.lib.FBO;
import com.bip.funnycamera.lib.GlObject;
import com.bip.funnycamera.lib.HoGLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MagGlSurface extends HoGLSurfaceView {

    static final float f61 = 0.15f;
    public Size FBOsize;
    public MODE Mode = MODE.MODE_1;
    private float Touch1X;
    private float Touch1X_old;
    private float Touch1Y;
    private float Touch1Y_old;

    private float f62Touch1X;

    private float f63Touch1Y;
    private float Touch2X;
    private float Touch2X_old;
    private float Touch2Y;
    private float Touch2Y_old;

    private float f64Touch2X;

    private float f65Touch2Y;
    private Cursor cursor;
    private FBO fbo;

    public boolean f17 = false;

    private ByteBuffer byteBuffer = null;

    public Bitmap imageBitmap = null;

    public Camera mCamera;

    public float f68 = 0.001f;

    static /* synthetic */ class C00071 {
        static final /* synthetic */ int[] $SwitchMap$com$ho$ho$magcamera$MagGlSurface$MODE = new int[MODE.values().length];

        static {
            try {
                $SwitchMap$com$ho$ho$magcamera$MagGlSurface$MODE[MODE.MODE_1.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$ho$ho$magcamera$MagGlSurface$MODE[MODE.MODE_2.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$ho$ho$magcamera$MagGlSurface$MODE[MODE.MODE_3.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$ho$ho$magcamera$MagGlSurface$MODE[MODE.MODE_4.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public enum MODE {
        MODE_1,
        MODE_2,
        MODE_3,
        MODE_4
    }

    public MagGlSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
        this.mCamera = new Camera(getContext(), 60, 60);
        this.cursor = new Cursor();
        this.FBOsize = this.mCamera.mCameraTexture.PreviewSize;
        this.fbo = new FBO(this.FBOsize.height, this.FBOsize.width);
    }

    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);
        this.fbo.Start();
        this.mCamera.Draw(GlObject.Identity);
        if (this.f17) {
            this.f17 = false;
            this.byteBuffer = ByteBuffer.allocateDirect(this.fbo.Width * this.fbo.Height * 4);
            this.byteBuffer.order(ByteOrder.nativeOrder());
            GLES20.glReadPixels(0, 0, this.fbo.Width, this.fbo.Height, 6408, 5121, this.byteBuffer);
            byte[] src = this.byteBuffer.array();
            int[] tmp = new int[(this.fbo.Width * this.fbo.Height)];
            int srci = 0;
            int desi = this.fbo.Width * (this.fbo.Height - 1);
            for (int y = 0; y < this.fbo.Height; y++) {
                for (int x = 0; x < this.fbo.Width; x++) {
                    tmp[desi + x] = -16777216 | ((src[srci + 0] & 255) << 16) | ((src[srci + 1] & 255) << 8) | (src[srci + 2] & 255);
                    srci += 4;
                }
                desi -= this.fbo.Width;
            }
            this.imageBitmap = Bitmap.createBitmap(tmp, this.fbo.Width, this.fbo.Height, Config.ARGB_8888);
        }
        this.fbo.End();
        this.cursor.Draw(true);
        this.fbo.f56.Draw();
    }

    /* access modifiers changed from: protected */
    public boolean OnTouch(MotionEvent ev) {
        this.Touch1X_old = this.Touch1X;
        this.Touch1Y_old = this.Touch1Y;
        this.Touch1X = ((ev.getX() / ((float) this.Width)) * 2.0f) - 1.0f;
        this.Touch1Y = -(((ev.getY() / ((float) this.Height)) * 2.0f) - 1.0f);
        int cnt = ev.getPointerCount();
        if (cnt > 1) {
            this.Touch2X_old = this.Touch2X;
            this.Touch2Y_old = this.Touch2Y;
            this.Touch2X = ((ev.getX(1) / ((float) this.Width)) * 2.0f) - 1.0f;
            this.Touch2Y = -(((ev.getY(1) / ((float) this.Height)) * 2.0f) - 1.0f);
        }
        switch (ev.getActionMasked()) {
            case 0:
                this.f62Touch1X = this.Touch1X;
                this.f63Touch1Y = this.Touch1Y;
                this.cursor.Touch1_Down(this.Touch1X, this.Touch1Y);
                switch (C00071.$SwitchMap$com$ho$ho$magcamera$MagGlSurface$MODE[this.Mode.ordinal()]) {
                    case 1:
                    case 2:
                        this.mCamera.mo14(this.Touch1X, this.Touch1Y, this.f68);
                        break;
                    case 3:
                        this.mCamera.m7(this.Touch1X, this.Touch1Y);
                        break;
                    case 4:
                        this.Touch2X = this.Touch1X;
                        this.Touch2Y = this.Touch1Y;
                        break;
                }
            case 1:
                this.cursor.Touch1_Up();
                break;
            case 2:
                if (!(this.Touch1X == this.Touch1X_old && this.Touch1Y == this.Touch1Y_old && this.Touch2X == this.Touch2X_old && this.Touch2Y == this.Touch2Y_old)) {
                    switch (C00071.$SwitchMap$com$ho$ho$magcamera$MagGlSurface$MODE[this.Mode.ordinal()]) {
                        case 1:
                        case 2:
                            this.mCamera.mo14(this.Touch1X, this.Touch1Y, this.f68);
                            break;
                        case 3:
                            this.cursor.m9set(this.Touch1X, this.Touch1Y);
                            float dx = this.Touch1X - this.f62Touch1X;
                            float dy = this.Touch1Y - this.f63Touch1Y;
                            if ((dx * dx) + (dy * dy) < 0.0225f) {
                                this.cursor.CancelLine();
                            }
                            this.mCamera.mo10(this.Touch1X, this.Touch1Y, f61);
                            break;
                        case 4:
                            this.cursor.m10set(this.Touch1X, this.Touch1Y);
                            if (cnt <= 1) {
                                this.cursor.m9set(this.Touch1X, this.Touch1Y);
                                break;
                            } else {
                                this.cursor.f50 = true;
                                this.cursor.m9set(this.Touch2X, this.Touch2Y);
                                this.mCamera.Reverse(this.Touch1X, this.Touch1Y, this.Touch2X, this.Touch2Y);
                                break;
                            }
                    }
                }
                break;
            case 5:
                this.f64Touch2X = this.Touch2X;
                this.f65Touch2Y = this.Touch2Y;
                switch (C00071.$SwitchMap$com$ho$ho$magcamera$MagGlSurface$MODE[this.Mode.ordinal()]) {
                    case 4:
                        this.mCamera.m6(this.Touch1X, this.Touch1Y, this.Touch2X, this.Touch2Y);
                        break;
                }
        }
        return true;
    }
}
