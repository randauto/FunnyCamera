package com.bip.funnycamera.lib;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.FloatBuffer;

public class Cursor extends Model {

    private static final short[] f4IB = {0, 1, 2, 3, 4, 5, 6, 7, 3, 8, 9, 6, 10, 11, 8, 5, 12, 0, 2, 13, 10, 10, 8, 6, 2, 10, 6, 2, 6, 3, 2, 3, 5, 2, 5, 0};

    private static final float f47 = 0.5f;
    private static final float[] f5VB = {0.31175f, -0.39092f, 0.0f, 0.90097f, -0.43388f, 0.0f, f47, 0.0f, 0.0f, -0.45048f, -0.21694f, 0.0f, -0.62349f, -0.78183f, 0.0f, -0.11126f, -0.48746f, 0.0f, -0.45048f, 0.21694f, 0.0f, -1.0f, 0.0f, 0.0f, -0.11126f, 0.48746f, 0.0f, -0.62349f, 0.78183f, 0.0f, 0.31174f, 0.39092f, 0.0f, 0.22252f, 0.97493f, 0.0f, 0.22252f, -0.97493f, 0.0f, 0.90097f, 0.43388f, 0.0f};
    private static float[] mLines = {0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f};
    private static FloatBuffer VB_line = RawResourceReader.DirectBuffer(mLines);


    private static final float f48 = 0.07f;

    private static final float f49 = 0.07692308f;
    private float mAngle;

    public boolean f50 = false;

    private float f51X;

    private float f52Y;

    private float f53 = 1.0f;

    private float f54X;

    private float f55Y;

    public Cursor() {
        super(f5VB, f4IB);
    }

    public void Draw(boolean line) {
        if (this.f53 < 1.0f) {
            float x = this.f54X;
            float y = this.f55Y;
            if (line && this.f50) {
                x = (this.f51X + x) * f47;
                y = (this.f52Y + y) * f47;
            }
            float[] m = m11Matrix(x, y, -0.1f);
            Matrix.rotateM(m, 0, this.mAngle, 0.0f, 0.0f, 1.0f);
            float zoom = (0.43f * this.f53) + f48;
            Matrix.scaleM(m, 0, zoom, zoom, 1.0f);
            SetColor(1.0f, 1.0f, 1.0f, 0.15f);
            super.Draw(m);
            this.mAngle += 9.0f;
            this.mAngle %= 360.0f;
            this.f53 -= f49;
            if (this.f53 < 0.0f) {
                this.f53 = 0.0f;
            }
            if (line) {
                mLines[0] = this.f54X;
                mLines[1] = this.f55Y;
                mLines[3] = this.f51X;
                mLines[4] = this.f52Y;
                float[] fArr = mLines;
                mLines[2] = -0.2f;
                fArr[5] = -0.2f;
                VB_line.put(mLines);
                VB_line.position(0);
                GLES20.glUniform4f(this.uColorHandle, 1.0f, f47, f47, f47);
                GLES20.glVertexAttribPointer(this.aPositionHandle, 3, 5126, false, 12, VB_line);
                GLES20.glUniformMatrix4fv(this.uMVPMatrixHandle, 1, false, Identity, 0);
                GLES20.glLineWidth(5.0f);
                GLES20.glEnable(3042);
                GLES20.glDrawArrays(1, 0, 2);
                GLES20.glDisable(3042);
            }
        }
    }

    public void Touch1_Down(float x, float y) {
        this.f51X = x;
        this.f54X = x;
        this.f52Y = y;
        this.f55Y = y;
        this.f50 = false;
        this.f53 = 0.999f;
    }

    public void Touch1_Up() {
        this.f53 = 1.0f;
    }

    public void m10set(float x, float y) {
        this.f54X = x;
        this.f55Y = y;
    }

    public void m9set(float x, float y) {
        this.f51X = x;
        this.f52Y = y;
    }

    public void CancelLine() {
        this.f51X = this.f54X;
        this.f52Y = this.f55Y;
    }
}
