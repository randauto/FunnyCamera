package com.bip.funnycamera.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import com.p000ho.p001ho.magcamera.BuildConfig;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/* renamed from: com.ho.ho.holib.IO */
public final class C0006IO {
    static double P_old;
    static long TotalTime_old;
    static long UseTime_old;
    static final File file = new File("proc/stat");

    /* renamed from: fr */
    static FileReader f9fr = null;

    /* renamed from: GetCPU使用率 reason: contains not printable characters */
    public static final String m14GetCPU() {
        try {
            String[] split = new BufferedReader(new FileReader(file)).readLine().split("\\s+");
            long system = (long) Integer.parseInt(split[3]);
            long iowait = (long) Integer.parseInt(split[5]);
            long irq = (long) Integer.parseInt(split[6]);
            long UseTime = ((long) Integer.parseInt(split[1])) + ((long) Integer.parseInt(split[2])) + system + irq + ((long) Integer.parseInt(split[7]));
            long TotalTime = UseTime + ((long) Integer.parseInt(split[4])) + iowait;
            double P = ((double) (UseTime - UseTime_old)) / ((double) (TotalTime - TotalTime_old));
            double tmp = (P_old + P) * 0.5d;
            UseTime_old = UseTime;
            TotalTime_old = TotalTime;
            P_old = P;
            return String.format("%.2f%%", new Object[]{Double.valueOf(100.0d * tmp)});
        } catch (Exception e) {
            return BuildConfig.FLAVOR;
        }
    }

    public static Bitmap CreateBitmap(Context context, int resID) {
        return BitmapFactory.decodeResource(context.getResources(), resID);
    }

    public static int CreateTexture2D(Bitmap bmp) {
        int[] txs = new int[1];
        GLES20.glGenTextures(1, txs, 0);
        if (txs[0] == 0) {
            throw new RuntimeException("Error loading texture.");
        }
        int textureID = txs[0];
        GLES20.glBindTexture(3553, textureID);
        GLUtils.texImage2D(3553, 0, bmp, 0);
        GLES20.glTexParameteri(3553, 10241, 9728);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        bmp.recycle();
        return textureID;
    }

    public static int CreateCameraTexture2D() {
        int[] txs = new int[1];
        GLES20.glGenTextures(1, txs, 0);
        m15GL("glGenTextures");
        if (txs[0] == 0) {
            throw new RuntimeException("Error loading texture.");
        }
        int textureID = txs[0];
        GLES20.glBindTexture(36197, textureID);
        m15GL("glBindTexture");
        GLES20.glTexParameteri(36197, 10241, 9728);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        m15GL("glTexParameteri");
        return textureID;
    }

    public static String readTextFileFromRawResource(Context context, int resourceId) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(resourceId)));
        StringBuilder body = new StringBuilder();
        while (true) {
            try {
                String nextLine = bufferedReader.readLine();
                if (nextLine == null) {
                    return body.toString();
                }
                body.append(nextLine);
                body.append(10);
            } catch (IOException e) {
                return null;
            }
        }
    }

    /* renamed from: 檢查GL錯誤 reason: contains not printable characters */
    public static void m15GL(String glOperation) {
        int error = GLES20.glGetError();
        if (error != 0) {
            String errStr = BuildConfig.FLAVOR;
            while (error != 0) {
                errStr = errStr + error + " , ";
                error = GLES20.glGetError();
            }
            Log.e("檢查 OpenGL : " + glOperation, errStr);
        }
    }

    public static FloatBuffer DirectBuffer(float[] ff) {
        ByteBuffer tmp = ByteBuffer.allocateDirect(ff.length * 4);
        tmp.order(ByteOrder.nativeOrder());
        FloatBuffer fb = tmp.asFloatBuffer();
        fb.put(ff);
        fb.position(0);
        return fb;
    }

    public static ShortBuffer DirectBuffer(short[] sf) {
        ByteBuffer tmp = ByteBuffer.allocateDirect(sf.length * 2);
        tmp.order(ByteOrder.nativeOrder());
        ShortBuffer sb = tmp.asShortBuffer();
        sb.put(sf);
        sb.position(0);
        return sb;
    }
}
