package com.bip.funnycamera.lib;

import android.opengl.GLES20;

/* renamed from: com.ho.ho.holib.FBO */
public class FBO {
    private int[] BackupViewport = new int[4];
    public int Height;

    /* renamed from: ID */
    private int f6ID = 0;
    public int Width;
    private int depthID;
    private int textureID;

    /* renamed from: 繪製目標 reason: contains not printable characters */
    public Obj2D f56;

    public FBO(int width, int height) {
        this.Width = width;
        this.Height = height;
        int[] tb = new int[1];
        GLES20.glGenTextures(1, tb, 0);
        this.textureID = tb[0];
        GLES20.glBindTexture(3553, this.textureID);
        GLES20.glTexImage2D(3553, 0, 6408, width, height, 0, 6408, 5121, null);
        GLES20.glTexParameteri(3553, 10241, 9728);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glBindTexture(3553, 0);
        int[] db = new int[1];
        GLES20.glGenRenderbuffers(1, db, 0);
        this.depthID = db[0];
        GLES20.glBindRenderbuffer(36161, this.depthID);
        GLES20.glRenderbufferStorage(36161, 33189, width, height);
        C0006IO.m15GL("glRenderbufferStorage (深度Buffer)");
        GLES20.glBindRenderbuffer(36161, 0);
        int[] fb = new int[1];
        GLES20.glGenFramebuffers(1, fb, 0);
        this.f6ID = fb[0];
        GLES20.glBindFramebuffer(36160, this.f6ID);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.textureID, 0);
        GLES20.glFramebufferRenderbuffer(36160, 36096, 36161, this.depthID);
        if (GLES20.glCheckFramebufferStatus(36160) != 36053) {
            C0006IO.m15GL("glCheckFramebufferStatus");
        }
        GLES20.glBindFramebuffer(36160, 0);
        this.f56 = new Obj2D(this.textureID);
    }

    public void Start() {
        GLES20.glGetIntegerv(2978, this.BackupViewport, 0);
        GLES20.glBindFramebuffer(36160, this.f6ID);
        GLES20.glViewport(0, 0, this.Width, this.Height);
        GLES20.glClear(16640);
    }

    public void End() {
        GLES20.glBindFramebuffer(36160, 0);
        GLES20.glViewport(this.BackupViewport[0], this.BackupViewport[1], this.BackupViewport[2], this.BackupViewport[3]);
        GLES20.glClear(16640);
    }
}
