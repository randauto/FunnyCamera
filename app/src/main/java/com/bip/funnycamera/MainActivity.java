package com.bip.funnycamera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.p000ho.p001ho.holib.Camera;
import com.p000ho.p001ho.holib.HoTimer;
import com.p000ho.p001ho.holib.ImageButtonPlus;
import com.p000ho.p001ho.magcamera.MagGlSurface.MODE;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* renamed from: com.ho.ho.magcamera.MainActivity */
public class MainActivity extends Activity {
    ImageButtonPlus[] btns;
    TextView fps;
    MagGlSurface mgs;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C0010R.layout.activity_main);
        this.mgs = (MagGlSurface) findViewById(C0010R.C0011id.view);
        this.fps = (TextView) findViewById(C0010R.C0011id.textView_FPS);
        ((Button) findViewById(C0010R.C0011id.f69button)).setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                MainActivity.this.m19OnTouch(v, event);
                return false;
            }
        });
        new HoTimer(133) {
            /* access modifiers changed from: protected */
            public void Tick() {
                MainActivity.this.TimeTick();
            }
        };
        this.btns = new ImageButtonPlus[4];
        this.btns[0] = (ImageButtonPlus) findViewById(C0010R.C0011id.f71imageButton);
        this.btns[1] = (ImageButtonPlus) findViewById(C0010R.C0011id.f72imageButton);
        this.btns[2] = (ImageButtonPlus) findViewById(C0010R.C0011id.f74imageButton);
        this.btns[3] = (ImageButtonPlus) findViewById(C0010R.C0011id.f73imageButton);
        this.btns[0].f57Enabled = true;
    }

    /* access modifiers changed from: 0000 */
    public void TimeTick() {
        if (this.mgs.f18 != null) {
            Camera r0 = this.mgs.f18;
            String format = String.format("GL:%.2f , CAM:%.2f 對焦=%d", new Object[]{Double.valueOf(this.mgs.FPS), Double.valueOf(r0.mCameraTexture.FPS), Integer.valueOf(r0.mCameraTexture.f23)});
            this.fps.setText(String.format("fps:%.2f", new Object[]{Double.valueOf(this.mgs.FPS)}));
            r0.f46 = ((CheckBox) findViewById(C0010R.C0011id.f70checkBox)).isChecked();
            if (this.mgs.f67bitmap != null) {
                SaveFile(this.mgs.f67bitmap);
                this.mgs.f67bitmap = null;
            }
        }
    }

    private void SaveFile(Bitmap bmp) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString(), "HoCamera" + new SimpleDateFormat("yyyy_MM_dd hh mm ss SSS").format(new Date()) + ".jpg");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, 30, out);
            out.flush();
            out.close();
            Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            mediaScanIntent.setData(Uri.fromFile(file));
            sendBroadcast(mediaScanIntent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void OnCameraChange(View v) {
        this.mgs.f18.mCameraTexture.ChangeCamera();
    }

    public void OnPause(View v) {
        this.mgs.f18.f3 = !this.mgs.f18.f3;
    }

    /* renamed from: On凸 reason: contains not printable characters */
    public void m20On(View v) {
        this.mgs.Mode = MODE.f22;
        this.mgs.f68 = 0.001f;
        m18(v);
    }

    /* renamed from: On凹 reason: contains not printable characters */
    public void m21On(View v) {
        this.mgs.Mode = MODE.f19;
        this.mgs.f68 = -0.001f;
        m18(v);
    }

    public void OnGridMove(View v) {
        this.mgs.Mode = MODE.f21;
        this.mgs.f68 = 0.0f;
        m18(v);
    }

    /* renamed from: On扭轉 reason: contains not printable characters */
    public void m22On(View v) {
        this.mgs.Mode = MODE.f20;
        m18(v);
    }

    /* renamed from: 改變選擇框 reason: contains not printable characters */
    private void m18(View v) {
        for (int i = 0; i < this.btns.length; i++) {
            this.btns[i].f57Enabled = this.btns[i] == v;
            this.btns[i].invalidate();
        }
    }

    /* renamed from: OnTouch原圖 reason: contains not printable characters */
    public void m19OnTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.mgs.f18.Save();
                this.mgs.f18.m8();
                return;
            default:
                return;
        }
    }

    public void OnExit(View v) {
        System.exit(168);
    }

    public void OnSave(View v) {
        this.mgs.f17 = true;
    }

    public void Onxxx(View v) {
    }
}
