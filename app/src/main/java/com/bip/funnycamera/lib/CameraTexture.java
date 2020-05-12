package com.bip.funnycamera.lib;

import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Face;
import android.hardware.Camera.FaceDetectionListener;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.opengl.Matrix;
import android.util.Log;
import java.io.IOException;
import java.util.List;

/* renamed from: com.ho.ho.holib.CameraTexture */
public class CameraTexture extends SurfaceTexture {
    protected int CameraID = 0;
    protected int CameraIDs;
    public double FPS;
    Rect FaceRect;
    private FaceDetectionListener OnFace = new FaceDetectionListener() {
        public void onFaceDetection(Face[] faces, Camera camera) {
            CameraTexture.this.FaceRect = faces[0].rect;
        }
    };
    private final OnFrameAvailableListener OnFrameCamera = new OnFrameAvailableListener() {
        private FPS fps = new FPS();
        private long oldt = System.nanoTime();

        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            CameraTexture.this.FPS = this.fps.Update();
            if (CameraTexture.this.f24) {
                long t = System.nanoTime();
                if (CameraTexture.this.f27 != 0.0d && ((double) (t - this.oldt)) * 1.0E-9d >= CameraTexture.this.f27) {
                    CameraTexture.this.mCamera.autoFocus(CameraTexture.this.afc);
                    this.oldt = t;
                }
            }
        }
    };
    public Size PreviewSize;
    public float[] Rate;
    /* access modifiers changed from: private */
    public AutoFocusCallback afc = new AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            if (success) {
                CameraTexture.this.f23++;
            }
        }
    };
    /* access modifiers changed from: private */
    public Camera mCamera = null;

    /* renamed from: 對焦成功數 reason: contains not printable characters */
    public int f23 = 0;

    /* renamed from: 支援自動對焦 reason: contains not printable characters */
    protected boolean f24 = false;

    /* renamed from: 校正反矩陣 reason: contains not printable characters */
    public float[] f25 = new float[16];

    /* renamed from: 校正矩陣 reason: contains not printable characters */
    public float[] f26 = new float[16];

    /* renamed from: 自動對焦秒 reason: contains not printable characters */
    public double f27 = 5.0d;

    public CameraTexture(int textureID) {
        super(textureID);
        setOnFrameAvailableListener(this.OnFrameCamera);
        this.CameraIDs = Camera.getNumberOfCameras();
        ChangeCamera();
    }

    public void ChangeCamera() {
        int tmp = this.CameraID;
        this.CameraID++;
        this.CameraID %= this.CameraIDs;
        if (tmp != this.CameraID) {
            if (this.mCamera != null) {
                this.mCamera.stopPreview();
                this.mCamera.release();
            }
            try {
                this.mCamera = Camera.open(this.CameraID);
                this.mCamera.setPreviewTexture(this);
                Parameters p = this.mCamera.getParameters();
                if (p.getSupportedFocusModes().contains("auto")) {
                    this.f24 = true;
                    p.setFocusMode("auto");
                }
                List<Size> PrvSize = p.getSupportedPreviewSizes();
                this.Rate = new float[PrvSize.size()];
                int idx = -1;
                float d43 = 9999.0f;
                for (int i = 0; i < this.Rate.length; i++) {
                    this.Rate[i] = ((float) ((Size) PrvSize.get(i)).width) / ((float) ((Size) PrvSize.get(i)).height);
                    float dr = Math.abs(this.Rate[i] - 1.3333334f);
                    if (dr < d43) {
                        d43 = dr;
                        idx = i;
                    }
                }
                this.PreviewSize = (Size) PrvSize.get(idx);
                p.setPreviewSize(this.PreviewSize.width, this.PreviewSize.height);
                this.mCamera.setParameters(p);
                this.mCamera.startPreview();
                CameraInfo info = new CameraInfo();
                Camera.getCameraInfo(this.CameraID, info);
                Matrix.setIdentityM(this.f26, 0);
                Matrix.rotateM(this.f26, 0, (float) info.orientation, 0.0f, 0.0f, -1.0f);
                if (info.facing != 0) {
                    Matrix.scaleM(this.f26, 0, 1.0f, -1.0f, 1.0f);
                }
                Matrix.invertM(this.f25, 0, this.f26, 0);
            } catch (IOException e) {
                Log.e("Camera", "Camera 錯誤!!!");
                e.printStackTrace();
            }
        }
    }
}
