package com.bip.funnycamera.lib;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;

public class GlObject {
    public static final float[] Identity = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    private static final String TAG = "GlObject: ";
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
        m12(RawResourceReader.readTextFileFromRawResourceOld(context2, VS), RawResourceReader.readTextFileFromRawResourceOld(context2, PS));
    }

    private void m12(String VS, String PS) {
        int vertexShader = loadShader(GL_VERTEX_SHADER, VS);
        int fragmentShader = loadShader(GL_FRAGMENT_SHADER, PS);
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
        RawResourceReader.m15GL("GlObject.共用建構子(VS,PS)");
    }

    public void SetColor(float R, float G, float B, float A) {
        this.Color[0] = R;
        this.Color[1] = G;
        this.Color[2] = B;
        this.Color[3] = A;
    }

    public static int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                Log.e(TAG, "Could not compile shader " + shaderType + ":");
                Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }


    public static float[] m11Matrix(float x, float y, float z) {
        return new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, x, y, z, 1.0f};
    }
}
