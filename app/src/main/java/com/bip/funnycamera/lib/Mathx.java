package com.bip.funnycamera.lib;

/* renamed from: com.ho.ho.holib.Mathx */
public final class Mathx {
    public static float[] COS = null;
    public static final float PI2 = 6.2831855f;
    public static float[] SIN = null;

    /* renamed from: 三角函數分割數 reason: contains not printable characters */
    public static final int f58 = 4096;

    /* renamed from: 起始三角函數表 reason: contains not printable characters */
    public static void m16() {
        if (SIN == null) {
            SIN = new float[f58];
            COS = new float[f58];
            double q = 0.0d;
            for (int i = 0; i < 4096; i++) {
                SIN[i] = (float) Math.sin(q);
                COS[i] = (float) Math.cos(q);
                q += 0.0015339808305725455d;
            }
        }
    }

    public static float Qcos(float q) {
        return COS[(int) (q * 651.8986f)];
    }

    public static float Qsin(float q) {
        return SIN[(int) (q * 651.8986f)];
    }
}
