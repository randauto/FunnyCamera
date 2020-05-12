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

import com.bip.funnycamera.MagGlSurface.MODE;
import com.bip.funnycamera.lib.Camera;
import com.bip.funnycamera.lib.HoTimer;
import com.bip.funnycamera.lib.ImageButtonPlus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {
    ImageButtonPlus[] btns;
    TextView fps;
    MagGlSurface mgs;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.activity_main);
        this.mgs = (MagGlSurface) findViewById(R.id.view);
        this.fps = (TextView) findViewById(R.id.textView_FPS);
        ((Button) findViewById(R.id.btnRestore)).setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                MainActivity.this.m19OnTouch(v, event);
                return false;
            }
        });
        new HoTimer(133) {
            public void Tick() {
                MainActivity.this.TimeTick();
            }
        };
        this.btns = new ImageButtonPlus[4];
        this.btns[0] = (ImageButtonPlus) findViewById(R.id.imageButtonConvex);
        this.btns[1] = (ImageButtonPlus) findViewById(R.id.imageButtonConcave);
        this.btns[2] = (ImageButtonPlus) findViewById(R.id.imageButton拉扯);
        this.btns[3] = (ImageButtonPlus) findViewById(R.id.imageButton扭轉);
        this.btns[0].f57Enabled = true;
    }

    public void TimeTick() {
        if (this.mgs.mCamera != null) {
            Camera r0 = this.mgs.mCamera;
            String format = String.format("GL:%.2f , CAM:%.2f 對焦=%d", new Object[]{Double.valueOf(this.mgs.FPS), Double.valueOf(r0.mCameraTexture.FPS), Integer.valueOf(r0.mCameraTexture.f23)});
            this.fps.setText(String.format("fps:%.2f", new Object[]{Double.valueOf(this.mgs.FPS)}));
            r0.f46 = ((CheckBox) findViewById(R.id.checkBoxGrid)).isChecked();
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
        this.mgs.mCamera.mCameraTexture.ChangeCamera();
    }

    public void OnPause(View v) {
        this.mgs.mCamera.f3 = !this.mgs.mCamera.f3;
    }

    public void onConvex(View v) {
        this.mgs.Mode = MODE.MODE_1;
        this.mgs.f68 = 0.001f;
        m18(v);
    }

    public void onConcave(View v) {
        this.mgs.Mode = MODE.MODE_2;
        this.mgs.f68 = -0.001f;
        m18(v);
    }

    public void OnGridMove(View v) {
        this.mgs.Mode = MODE.MODE_3;
        this.mgs.f68 = 0.0f;
        m18(v);
    }

    public void Reverse(View v) {
        this.mgs.Mode = MODE.MODE_4;
        m18(v);
    }

    private void m18(View v) {
        for (int i = 0; i < this.btns.length; i++) {
            this.btns[i].f57Enabled = this.btns[i] == v;
            this.btns[i].invalidate();
        }
    }

    public void m19OnTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.mgs.mCamera.Save();
                this.mgs.mCamera.m8();
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
}
