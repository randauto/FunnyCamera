package com.bip.funnycamera;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bip.funnycamera.MagGlSurface.MODE;
import com.bip.funnycamera.lib.Camera;
import com.bip.funnycamera.lib.HoTimer;
import com.bip.funnycamera.lib.ImageButtonPlus;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {
    private ImageButtonPlus[] btns;

    private TextView fps;

    private MagGlSurface mgs;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.activity_main);
        initPermission();

        this.mgs = findViewById(R.id.view);
        this.fps = findViewById(R.id.textView_FPS);
        findViewById(R.id.btnRestore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoreStateCamera();
            }
        });

//        initTimerFps();

        this.btns = new ImageButtonPlus[4];
        this.btns[0] = findViewById(R.id.imageButtonConvex);
        this.btns[1] = findViewById(R.id.imageButtonConcave);
        this.btns[2] = findViewById(R.id.imageButtonOnGridMove);
        this.btns[3] = findViewById(R.id.imageButtonReverse);
        this.btns[0].isEnabled = true;

        initView();
    }

    private void initView() {
        findViewById(R.id.imageButton_change_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnCameraChange();
            }
        });

        findViewById(R.id.imageButton_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnPause();
            }
        });
    }

    private void initPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // open camera suface.
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void initTimerFps() {
        new HoTimer(133) {
            public void Tick() {
                TimeTick();
            }
        };
    }

    public void TimeTick() {
        if (this.mgs.mCamera != null) {
            Camera r0 = this.mgs.mCamera;
            this.fps.setText(String.format("fps:%.2f", new Object[]{Double.valueOf(this.mgs.FPS)}));
            r0.f46 = ((CheckBox) findViewById(R.id.checkBoxGrid)).isChecked();
            if (this.mgs.imageBitmap != null) {
                SaveFile(this.mgs.imageBitmap);
                this.mgs.imageBitmap = null;
            }
        }
    }

    private void SaveFile(Bitmap bmp) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString(), "BipCamera" + new SimpleDateFormat("yyyy_MM_dd hh mm ss SSS").format(new Date()) + ".jpg");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, 30, out);
            out.flush();
            out.close();
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(Uri.fromFile(file));
            sendBroadcast(mediaScanIntent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void OnCameraChange() {
        this.mgs.mCamera.mCameraTexture.ChangeCamera();
    }

    public void OnPause() {
        this.mgs.mCamera.f3 = !this.mgs.mCamera.f3;
    }

    public void onConvex(View v) {
        this.mgs.Mode = MODE.MODE_1;
        this.mgs.f68 = 0.001f;
        changeStateView(v);
    }

    public void onConcave(View v) {
        this.mgs.Mode = MODE.MODE_2;
        this.mgs.f68 = -0.001f;
        changeStateView(v);
    }

    public void OnGridMove(View v) {
        this.mgs.Mode = MODE.MODE_3;
        this.mgs.f68 = 0.0f;
        changeStateView(v);
    }

    public void Reverse(View v) {
        this.mgs.Mode = MODE.MODE_4;
        changeStateView(v);
    }

    private void changeStateView(View v) {
        for (int i = 0; i < this.btns.length; i++) {
            this.btns[i].isEnabled = this.btns[i] == v;
            this.btns[i].invalidate();
        }
    }

    public void restoreStateCamera() {
        this.mgs.mCamera.Save();
        this.mgs.mCamera.m8();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void OnExit(View v) {
        finish();
    }

    public void OnSave(View v) {
        this.mgs.f17 = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(R.string.txt_alert_save_image);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
