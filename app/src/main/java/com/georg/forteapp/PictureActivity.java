package com.georg.forteapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class PictureActivity extends AppCompatActivity {

    public static final String IS_FIRST = "isFirst";
    private ImageView iv;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        iv = (ImageView) findViewById(R.id.iv);

        if (getIntent().getBooleanExtra(IS_FIRST,true)){
            path = MainActivity.PATH_ONE;
        }
        else {
            path = MainActivity.PATH_TWO;
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    refreshImage();
                }
                else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Utils.showExplainPermissionDialog(this);
                    }else {
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MainActivity.MY_PERMISSION_REQUEST);
                    }
                }
            }
            else {
                refreshImage();
            }
        }
    }

    private void refreshImage() {
        File file = new File(MainActivity.DOWNLOAD_DIR,path);

        DecodeBitmapFromFileTask task = new DecodeBitmapFromFileTask(iv,getResources());
        task.execute(new SampleBitmapData(file.getAbsolutePath(),iv.getWidth(), iv.getHeight()));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MainActivity.MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //refreshImage();
                } else {
                    Utils.showExplainPermissionDialog(this);
                }
                return;
            }
        }
    }

}
