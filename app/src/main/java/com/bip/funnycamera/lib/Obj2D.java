package com.bip.funnycamera.lib;

import android.graphics.Bitmap;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Obj2D extends GlObject {

    private static final String f14PS = "precision mediump  float ;uniform sampler2D  uTexture;uniform vec4 uColor;varying vec2  vUV;void main()\t{vec4 c =  texture2D( uTexture , vUV );gl_FragColor = c * uColor;}";

    private static final String f15VS = "uniform mat4 uMVPMatrix;attribute vec4 aPosition;attribute vec2 aUV;varying vec2 vUV;void main()\t{gl_Position = uMVPMatrix  *   aPosition;vUV =aUV;\t}";

    private static final float[] f60UV = {-1.0f, 1.0f, 0.0f, 0.0f, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f};

    private FloatBuffer f16VB;

    public Obj2D(int textureID) {
        super(f15VS, f14PS);
        m17();
        this.TextureID = textureID;
    }

    public Obj2D(Bitmap bmp) {
        super(f15VS, f14PS);
        m17();
        this.TextureID = C0006IO.CreateTexture2D(bmp);
    }

    private void m17() {
        ByteBuffer tmp = ByteBuffer.allocateDirect(f60UV.length * 4);
        tmp.order(ByteOrder.nativeOrder());
        this.f16VB = tmp.asFloatBuffer();
        this.f16VB.put(f60UV);
        this.f16VB.position(0);
    }

    public void Draw() {
        Draw(Identity);
    }

    public void Draw(float[] mvpMatrix) {
        GLES20.glUseProgram(this.mProgram);
        this.f16VB.position(0);
        GLES20.glVertexAttribPointer(this.aPositionHandle, 3, 5126, false, 20, this.f16VB);
        this.f16VB.position(3);
        GLES20.glVertexAttribPointer(this.aUVHandle, 2, 5126, false, 20, this.f16VB);
        GLES20.glUniformMatrix4fv(this.uMVPMatrixHandle, 1, false, mvpMatrix, 0);
        GLES20.glBindTexture(3553, this.TextureID);
        GLES20.glUniform1i(this.uTextureHandle, 0);
        GLES20.glUniform4f(this.uColorHandle, this.Color[0], this.Color[1], this.Color[2], this.Color[3]);
        GLES20.glDrawArrays(5, 0, 4);
    }
}
