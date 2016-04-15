package com.georg.forteapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final int MY_PERMISSION_REQUEST = 1;
    public static final String PATH_ONE = "1.jpg";
    public static final String PATH_TWO = "2.jpg";
    public static File DOWNLOAD_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    private RadioButton first;
    private RadioButton second;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        first = (RadioButton) findViewById(R.id.rb_one);
        second = (RadioButton) findViewById(R.id.rb_two);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (first.getCompoundDrawables()[2]!=null && second.getCompoundDrawables()[2]!=null) {
                    Intent intent = new Intent(MainActivity.this, PictureActivity.class);
                    intent.putExtra(PictureActivity.IS_FIRST, first.isChecked());
                    startActivity(intent);
                }else {
                    Snackbar.make(view, R.string.put_images_mess, Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    refreshImages();
                }
                else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Utils.showExplainPermissionDialog(this);
                    }else {
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
                    }
                }
            }
            else {
                refreshImages();
            }
        }
    }

    private void refreshImages() {
        setDrawableToRadioButton(first, PATH_ONE);
        setDrawableToRadioButton(second, PATH_TWO);
    }

    private void setDrawableToRadioButton(RadioButton rb, String filePath){
        File file = new File(DOWNLOAD_DIR,filePath);

        DecodeBitmapFromFileTask task = new DecodeBitmapFromFileTask(rb,getResources());
        task.execute(new SampleBitmapData(file.getAbsolutePath(),rb.getWidth(), rb.getHeight()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //refreshImages();
                } else {
                    Utils.showExplainPermissionDialog(this);
                }
                return;
            }
        }
    }

}
