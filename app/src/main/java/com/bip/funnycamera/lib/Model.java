package com.bip.funnycamera.lib;

import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Model extends GlObject {

    private static final String f10PS = "precision mediump  float ;uniform vec4 uColor;void main()\t{ gl_FragColor= uColor; }";

    private static final String f11VS = "uniform mat4 uMVPMatrix;attribute vec4 aPosition;void main()\t{ gl_Position = uMVPMatrix * aPosition; }";

    private ShortBuffer f12IB;

    private FloatBuffer f13VB;

    private int f59;

    public Model(float[] vb, short[] ib) {
        super(f11VS, f10PS);
        this.f13VB = C0006IO.DirectBuffer(vb);
        this.f12IB = C0006IO.DirectBuffer(ib);
        this.f59 = ib.length;
    }

    public void Draw(float[] matrix) {
        GLES20.glUseProgram(this.mProgram);
        this.f13VB.position(0);
        GLES20.glVertexAttribPointer(this.aPositionHandle, 3, 5126, false, 12, this.f13VB);
        GLES20.glUniformMatrix4fv(this.uMVPMatrixHandle, 1, false, matrix, 0);
        GLES20.glUniform4f(this.uColorHandle, this.Color[0], this.Color[1], this.Color[2], this.Color[3]);
        GLES20.glEnable(3042);
        GLES20.glBlendFunc(770, 1);
        GLES20.glDrawElements(4, this.f59, 5123, this.f12IB);
        GLES20.glDisable(3042);
    }
}
