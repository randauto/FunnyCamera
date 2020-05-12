package com.bip.funnycamera.lib;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.bip.funnycamera.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Camera extends GlObject {

    private static final float f28 = 0.1f;

    private static final float f29 = 0.9f;

    private static final int f30_X = 0;

    private static final int f31_Y = 1;

    private static final int f32_oX = 2;

    private static final int f33_oY = 3;

    private static final int f34_sX = 4;

    private static final int f35_sY = 5;

    private static final int f36 = 6;
    public float[] Color = {0.7f, 0.7f, 0.7f, 1.0f};

    /* renamed from: IB */
    private ShortBuffer f0IB;
    private ShortBuffer IB_L;
    float[] SaveData = null;

    /* renamed from: VB */
    private FloatBuffer f1VB;
    public CameraTexture mCameraTexture;
    private short[] mIB;
    private short[] mIB_L;
    private float[] mVB;

    /* renamed from: mm */
    float[] f2mm = new float[4];
    float[] mmo = new float[4];

    private float[] f37m;
    Random32 rnd = new Random32();

    private int f38X;

    private int f39Y;

    private double f40;

    private float f41X;

    private float f42Y;

    public boolean f3 = false;

    private int f43;

    private int f44;

    private final float f45 = 0.98f;

    public boolean f46 = false;

    public Camera(Context context, int cX, int cY) {
        super(context, R.raw.o2d_vertex, R.raw.camera_fragment);
        this.f38X = cX;
        this.f39Y = cY;
        CreateMesh();
        ByteBuffer tmp = ByteBuffer.allocateDirect(this.mVB.length * 4);
        tmp.order(ByteOrder.nativeOrder());
        this.f1VB = tmp.asFloatBuffer();
        this.f1VB.put(this.mVB);
        ByteBuffer tmp2 = ByteBuffer.allocateDirect(this.mIB.length * f32_oX);
        tmp2.order(ByteOrder.nativeOrder());
        this.f0IB = tmp2.asShortBuffer();
        this.f0IB.put(this.mIB);
        this.f0IB.position(f30_X);
        ByteBuffer tmp3 = ByteBuffer.allocateDirect(this.mIB_L.length * f32_oX);
        tmp3.order(ByteOrder.nativeOrder());
        this.IB_L = tmp3.asShortBuffer();
        this.IB_L.put(this.mIB_L);
        this.IB_L.position(f30_X);
        this.TextureID = C0006IO.CreateCameraTexture2D();
        this.mCameraTexture = new CameraTexture(this.TextureID);
        GLES20.glDisable(2884);
    }

    public void Draw(float[] mvpMatrix) {
        m5_to_mVB();
        m4Next();
        this.f1VB.position(f30_X);
        this.f1VB.put(this.mVB);
        if (!this.f3) {
            this.mCameraTexture.updateTexImage();
        }
        GLES20.glUseProgram(this.mProgram);
        this.f1VB.position(f30_X);
        GLES20.glVertexAttribPointer(this.aPositionHandle, f33_oY, 5126, false, 20, this.f1VB);
        this.f1VB.position(f33_oY);
//        r8 = f30_X;
        GLES20.glVertexAttribPointer(this.aUVHandle, f32_oX, 5126, false, 20, this.f1VB);
        float[] mm = new float[16];
//        r8 = f30_X;
        float[] fArr = mvpMatrix;
//        r10 = f30_X;
//        r12 = f30_X;
        Matrix.multiplyMM(mm, f30_X, fArr, f30_X, this.mCameraTexture.f26, f30_X);
        GLES20.glUniformMatrix4fv(this.uMVPMatrixHandle, f31_Y, false, mm, f30_X);
        GLES20.glBindTexture(36197, this.TextureID);
        GLES20.glUniform1i(this.uTextureHandle, f30_X);
        GLES20.glUniform4f(this.uColorHandle, this.Color[f30_X], this.Color[f31_Y], this.Color[f32_oX], this.Color[f33_oY]);
        if (!this.f46) {
            GLES20.glDrawElements(4, this.f43 * f33_oY, 5123, this.f0IB);
            return;
        }
        GLES20.glLineWidth(4.0f);
        GLES20.glDrawElements(f31_Y, this.f43 * f32_oX, 5123, this.IB_L);
    }

    private void CreateMesh() {
        this.f44 = (this.f38X + f31_Y) * (this.f39Y + f31_Y);
        this.f43 = this.f38X * this.f39Y * f32_oX;
        this.f37m = new float[(this.f44 * f36)];
        m8();
        this.mVB = new float[(this.f44 * f35_sY)];
        this.mIB = new short[(this.f43 * f33_oY)];
        this.mIB_L = new short[(this.f43 * f32_oX)];
        int n = f30_X;
        for (int y = f30_X; y <= this.f39Y; y += f31_Y) {
            for (int x = f30_X; x <= this.f38X; x += f31_Y) {
                this.mVB[n + f30_X] = -1.0f + ((((float) x) * 2.0f) / ((float) this.f38X));
                this.mVB[n + f31_Y] = 1.0f - ((((float) y) * 2.0f) / ((float) this.f39Y));
                this.mVB[n + f32_oX] = f30_X;
                this.mVB[n + f33_oY] = ((float) x) / ((float) this.f38X);
                this.mVB[n + 4] = ((float) y) / ((float) this.f39Y);
                n += f35_sY;
            }
        }
        int nl = f30_X;
        int n2 = f30_X;
        for (int y2 = f30_X; y2 < this.f39Y; y2 += f31_Y) {
            for (int x2 = f30_X; x2 < this.f38X; x2 += f31_Y) {
                int off1 = ((this.f38X + f31_Y) * y2) + x2;
                int off2 = off1 + this.f38X + f31_Y;
                this.mIB[n2 + f30_X] = (short) off1;
                this.mIB[n2 + f31_Y] = (short) off2;
                this.mIB[n2 + f32_oX] = (short) (off1 + f31_Y);
                this.mIB[n2 + f33_oY] = (short) (off1 + f31_Y);
                this.mIB[n2 + 4] = (short) off2;
                this.mIB[n2 + f35_sY] = (short) (off2 + f31_Y);
                n2 += f36;
                this.mIB_L[nl + f30_X] = (short) off1;
                this.mIB_L[nl + f31_Y] = (short) off2;
                this.mIB_L[nl + f32_oX] = (short) off1;
                this.mIB_L[nl + f33_oY] = (short) (off1 + f31_Y);
                nl += 4;
            }
        }
    }

    private void m4Next() {
        int n = f30_X;
        for (int i = f30_X; i < this.f44; i += f31_Y) {
            float[] fArr = this.f37m;
            int i2 = n + f30_X;
            fArr[i2] = fArr[i2] + this.f37m[n + 4];
            float[] fArr2 = this.f37m;
            int i3 = n + f31_Y;
            fArr2[i3] = fArr2[i3] + this.f37m[n + f35_sY];
            float[] fArr3 = this.f37m;
            int i4 = n + 4;
            fArr3[i4] = fArr3[i4] * f29;
            float[] fArr4 = this.f37m;
            int i5 = n + f35_sY;
            fArr4[i5] = fArr4[i5] * f29;
            float[] fArr5 = this.f37m;
            int i6 = n + 4;
            fArr5[i6] = fArr5[i6] + ((this.f37m[n + f32_oX] - this.f37m[n + f30_X]) * f28);
            float[] fArr6 = this.f37m;
            int i7 = n + f35_sY;
            fArr6[i7] = fArr6[i7] + ((this.f37m[n + f33_oY] - this.f37m[n + f31_Y]) * f28);
            n += f36;
        }
    }

    private void m5_to_mVB() {
        int n1 = f30_X;
        int n2 = f30_X;
        for (int i = f30_X; i < this.f44; i += f31_Y) {
            this.mVB[n1 + f30_X] = this.f37m[n2 + f30_X];
            this.mVB[n1 + f31_Y] = this.f37m[n2 + f31_Y];
            n1 += f35_sY;
            n2 += f36;
        }
    }

    public void Save() {
        this.SaveData = new float[(this.f44 * f32_oX)];
        int n1 = f30_X;
        int n2 = f30_X;
        for (int i = f30_X; i < this.f44; i += f31_Y) {
            this.SaveData[n1 + f30_X] = this.f37m[n2 + f32_oX];
            this.SaveData[n1 + f31_Y] = this.f37m[n2 + f33_oY];
            n1 += f32_oX;
            n2 += f36;
        }
    }

    public void Load() {
        if (this.SaveData != null && this.SaveData.length >= this.f44 * f32_oX) {
            int n1 = f30_X;
            int n2 = f30_X;
            for (int i = f30_X; i < this.f44; i += f31_Y) {
                this.f37m[n2 + f32_oX] = this.SaveData[n1 + f30_X];
                this.f37m[n2 + f33_oY] = this.SaveData[n1 + f31_Y];
                n1 += f32_oX;
                n2 += f36;
            }
        }
    }

    public void m8() {
        int n = f30_X;
        for (int y = f30_X; y <= this.f39Y; y += f31_Y) {
            for (int x = f30_X; x <= this.f38X; x += f31_Y) {
                this.f37m[n + f32_oX] = -1.0f + ((((float) x) * 2.0f) / ((float) this.f38X));
                this.f37m[n + f33_oY] = 1.0f - ((((float) y) * 2.0f) / ((float) this.f39Y));
                n += f36;
            }
        }
    }

    public void mo12() {
        m8();
        int n = f30_X;
        for (int i = f30_X; i < this.f44; i += f31_Y) {
            float[] fArr = this.f37m;
            int i2 = n + 4;
            fArr[i2] = fArr[i2] + ((this.rnd.NextFloat() - 0.5f) * 0.02f);
            float[] fArr2 = this.f37m;
            int i3 = n + f35_sY;
            fArr2[i3] = fArr2[i3] + ((this.rnd.NextFloat() - 0.5f) * 0.02f);
            n += f36;
        }
    }

    public void mo14(float x, float y, float value) {
        this.f2mm[f30_X] = x;
        this.f2mm[f31_Y] = y;
        Matrix.multiplyMV(this.mmo, f30_X, this.mCameraTexture.f25, f30_X, this.f2mm, f30_X);
        float px = this.mmo[f30_X];
        float py = this.mmo[f31_Y];
        int n = f30_X;
        for (int i = f30_X; i < this.f44; i += f31_Y) {
            float dx = px - this.f37m[n + f30_X];
            float dy = py - this.f37m[n + f31_Y];
            float f = value / (((dx * dx) + (dy * dy)) + 0.05f);
            float[] fArr = this.f37m;
            int i2 = n + f32_oX;
            fArr[i2] = fArr[i2] + ((-dx) * f);
            float[] fArr2 = this.f37m;
            int i3 = n + f33_oY;
            fArr2[i3] = fArr2[i3] + ((-dy) * f);
            n += f36;
        }
    }

    public void m7(float x, float y) {
        Save();
        this.f2mm[f30_X] = x;
        this.f2mm[f31_Y] = y;
        Matrix.multiplyMV(this.mmo, f30_X, this.mCameraTexture.f25, f30_X, this.f2mm, f30_X);
        this.f41X = this.mmo[f30_X];
        this.f42Y = this.mmo[f31_Y];
    }

    public void mo10(float tx, float ty, float f) {
        float Tx;
        float Ty;
        this.f2mm[f30_X] = tx;
        this.f2mm[f31_Y] = ty;
        Matrix.multiplyMV(this.mmo, f30_X, this.mCameraTexture.f25, f30_X, this.f2mm, f30_X);
        float Tx2 = this.mmo[f30_X];
        float Tx3 = Tx2 - this.f41X;
        float Ty2 = this.mmo[f31_Y] - this.f42Y;
        float len = (float) Math.sqrt((double) ((Tx3 * Tx3) + (Ty2 * Ty2)));
        float f2 = len - f;
        if (tx > 0.98f || tx < -0.98f) {
            f2 = -1.0f;
        }
        if (ty > 0.98f || ty < -0.98f) {
            f2 = -1.0f;
        }
        if (f2 < 0.0f) {
            Ty = 0.0f;
            Tx = f30_X;
        } else {
            float f4 = f2 / len;
            Tx = Tx3 * f4;
            Ty = Ty2 * f4;
        }
        int n = f30_X;
        int n2 = f30_X;
        for (int i = f30_X; i < this.f44; i += f31_Y) {
            float dx = this.f41X - this.SaveData[n2 + f30_X];
            float dy = this.f42Y - this.SaveData[n2 + f31_Y];
            float f5 = 0.07f / (((dx * dx) + (dy * dy)) + 0.07f);
            this.f37m[n + f32_oX] = this.SaveData[n2 + f30_X] + (Tx * f5);
            this.f37m[n + f33_oY] = this.SaveData[n2 + f31_Y] + (Ty * f5);
            n += f36;
            n2 += f32_oX;
        }
    }

    public void m6(float x1, float y1, float x2, float y2) {
        Save();
        this.f2mm[f30_X] = x1;
        this.f2mm[f31_Y] = y1;
        Matrix.multiplyMV(this.mmo, f30_X, this.mCameraTexture.f25, f30_X, this.f2mm, f30_X);
        float x12 = this.mmo[f30_X];
        float y12 = this.mmo[f31_Y];
        this.f2mm[f30_X] = x2;
        this.f2mm[f31_Y] = y2;
        Matrix.multiplyMV(this.mmo, f30_X, this.mCameraTexture.f25, f30_X, this.f2mm, f30_X);
        this.f40 = Math.atan2((double) (this.mmo[f30_X] - x12), (double) (this.mmo[f31_Y] - y12));
    }

    public void Reverse(float x1, float y1, float x2, float y2) {
        this.f2mm[f30_X] = x1;
        this.f2mm[f31_Y] = y1;
        Matrix.multiplyMV(this.mmo, f30_X, this.mCameraTexture.f25, f30_X, this.f2mm, f30_X);
        float x12 = this.mmo[f30_X];
        float y12 = this.mmo[f31_Y];
        this.f2mm[f30_X] = x2;
        this.f2mm[f31_Y] = y2;
        Matrix.multiplyMV(this.mmo, f30_X, this.mCameraTexture.f25, f30_X, this.f2mm, f30_X);
        float x22 = this.mmo[f30_X];
        float y22 = this.mmo[f31_Y];
        float dx = x22 - x12;
        float dy = y22 - y12;
        double atan2 = Math.atan2((double) dx, (double) dy) - this.f40;
        if (atan2 > 3.141592653589793d) {
            atan2 -= 6.2831854820251465d;
        } else if (atan2 < -3.141592653589793d) {
            atan2 += 6.2831854820251465d;
        }
        float f = (x12 + x22) * 0.5f;
        float f2 = (y12 + y22) * 0.5f;
        float f4 = (float) (((double) ((dx * dx) + (dy * dy))) * 0.25d);
        int n = f30_X;
        int n2 = f30_X;
        for (int i = f30_X; i < this.f44; i += f31_Y) {
            float x = this.SaveData[n2 + f30_X];
            float y = this.SaveData[n2 + f31_Y];
            float dx2 = x - f;
            float dy2 = y - f2;
            float f5 = (dx2 * dx2) + (dy2 * dy2);
            if (f5 > f4) {
                this.f37m[n + f32_oX] = x;
                this.f37m[n + f33_oY] = y;
            } else {
                double d = atan2 * ((double) (1.0f - (f5 / f4)));
                double sin = Math.sin(d);
                double cos = Math.cos(d);
                this.f37m[n + f32_oX] = ((float) ((((double) dx2) * cos) + (((double) dy2) * sin))) + f;
                this.f37m[n + f33_oY] = ((float) ((((double) dy2) * cos) - (((double) dx2) * sin))) + f2;
            }
            n += f36;
            n2 += f32_oX;
        }
    }
}
