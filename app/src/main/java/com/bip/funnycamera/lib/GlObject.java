package com.bip.funnycamera.lib;

import android.content.Context;
import android.opengl.GLES20;

/* renamed from: com.ho.ho.holib.GlObject */
public class GlObject {
    public static final float[] Identity = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    public float[] Color = {1.0f, 1.0f, 1.0f, 1.0f};
    protected int TextureID;
    protected int aColorHandle;
    protected int aNormalHandle;
    protected int aPositionHandle;
    protected int aUVHandle;
    Context context;
    protected int mProgram;
    protected int uColorHandle;
    protected int uMVPMatrixHandle;
    protected int uTextureHandle;

    public GlObject(String VS, String PS) {
        m12(VS, PS);
    }

    public GlObject(Context context2, int VS, int PS) {
        m12(C0006IO.readTextFileFromRawResource(context2, VS), C0006IO.readTextFileFromRawResource(context2, PS));
    }

    /* renamed from: 共用建構子 reason: contains not printable characters */
    private void m12(String VS, String PS) {
        int vertexShader = m13(35633, VS);
        int fragmentShader = m13(35632, PS);
        this.mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(this.mProgram, vertexShader);
        GLES20.glAttachShader(this.mProgram, fragmentShader);
        GLES20.glLinkProgram(this.mProgram);
        this.aPositionHandle = GLES20.glGetAttribLocation(this.mProgram, "aPosition");
        this.aNormalHandle = GLES20.glGetAttribLocation(this.mProgram, "aNormal");
        this.aColorHandle = GLES20.glGetAttribLocation(this.mProgram, "aColor");
        this.aUVHandle = GLES20.glGetAttribLocation(this.mProgram, "aUV");
        GLES20.glEnableVertexAttribArray(this.aPositionHandle);
        GLES20.glEnableVertexAttribArray(this.aUVHandle);
        this.uMVPMatrixHandle = GLES20.glGetUniformLocation(this.mProgram, "uMVPMatrix");
        this.uTextureHandle = GLES20.glGetUniformLocation(this.mProgram, "uTexture");
        this.uColorHandle = GLES20.glGetUniformLocation(this.mProgram, "uColor");
        C0006IO.m15GL("GlObject.共用建構子(VS,PS)");
    }

    public void SetColor(float R, float G, float B, float A) {
        this.Color[0] = R;
        this.Color[1] = G;
        this.Color[2] = B;
        this.Color[3] = A;
    }

    /* renamed from: 編譯著色器 reason: contains not printable characters */
    public static int m13(int type, String str) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, str);
        GLES20.glCompileShader(shader);
        int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, 35713, compiled, 0);
        if (compiled[0] != 0) {
            return shader;
        }
        GLES20.glGetShaderiv(shader, 35716, compiled, 0);
        throw new RuntimeException("Shade編譯失敗...\r\n" + GLES20.glGetShaderInfoLog(shader));
    }

    /* renamed from: Matrix建立平移 reason: contains not printable characters */
    public static float[] m11Matrix(float x, float y, float z) {
        return new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, x, y, z, 1.0f};
    }
}
