package com.bip.funnycamera.lib;

import android.content.Context;
import android.opengl.EGL14;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES10.GL_TEXTURE0;
import static android.opengl.GLES20.GL_CULL_FACE;
import static android.opengl.GLES20.GL_DEPTH_TEST;

public class HoGLSurfaceView extends GLSurfaceView implements Renderer {
    public static HoGLSurfaceView GlobalSurfaceView;
    public double FPS;
    protected int Height;
    public boolean VSYNC = true;
    protected int Width;
    private FPS fps = new FPS();
    public float[] mProjectionMatrix = new float[16];
    public float[] mViewMatrix = new float[16];

    public HoGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        setRenderer(this);
        GlobalSurfaceView = this;
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent e) {
                return HoGLSurfaceView.this.OnTouch(e);
            }
        });
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
        RawResourceReader.m15GL("glClearColor");
        GLES20.glEnable(GL_DEPTH_TEST);
        RawResourceReader.m15GL("glEnable GL_DEPTH_TEST");
        GLES20.glEnable(GL_CULL_FACE);
        RawResourceReader.m15GL("glEnable GL_CULL_FACE");
        GLES20.glActiveTexture(GL_TEXTURE0);
        RawResourceReader.m15GL("glActiveTexture GL_TEXTURE0");
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.Width = width;
        this.Height = height;
        GLES20.glViewport(0, 0, this.Width, this.Height);
        Matrix.perspectiveM(this.mProjectionMatrix, 0, 45.0f, ((float) width) / ((float) height), 1.0f, 999.0f);
        Matrix.setLookAtM(this.mViewMatrix, 0, 0.0f, 0.0f, 50.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        if (!this.VSYNC && VERSION.SDK_INT >= 17) {
            EGL14.eglSwapInterval(EGL14.eglGetDisplay(0), 0);
        }
    }

    public void onDrawFrame(GL10 gl) {
        this.FPS = this.fps.Update();
        GLES20.glClear(16640);
    }

    /* access modifiers changed from: protected */
    public boolean OnTouch(MotionEvent ev) {
        return true;
    }
}
