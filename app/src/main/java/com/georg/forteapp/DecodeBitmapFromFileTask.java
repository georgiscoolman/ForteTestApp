package com.georg.forteapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.io.File;

/**
 * Created by Georg on 14.04.2016.
 */
public class DecodeBitmapFromFileTask extends AsyncTask<SampleBitmapData, Void, Bitmap> {

    private View target;
    private Resources resources;
    private File input;

    public DecodeBitmapFromFileTask(View target, Resources resources) {
        this.target = target;
        this.resources = resources;
    }

    @Override
    protected Bitmap doInBackground(SampleBitmapData... params) {
        SampleBitmapData data = params[0];
        input = new File(data.getFilePath());
        return decodeSampledBitmapFromFile(data.getFilePath(), data.getReqWidth(), data.getReqHeight());
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if (target instanceof RadioButton) {

            RadioButton rb = ((RadioButton) target);

            if (bitmap != null) {
                Drawable drawable = new BitmapDrawable(resources, bitmap);
                rb.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                rb.setText(null);
            } else {
                rb.setText("Please put \"" + input.getName() + "\" to \"" + input.getParent() + "\" directory");
            }
        }

        if (target instanceof ImageView) {
            ((ImageView) target).setImageBitmap(bitmap);
        }

        if (bitmap != null) {
            Log.d("Performance", String.format("Required size = %sx%s, bitmap size = %sx%s, byteCount = %s",
                    target.getWidth(), target.getHeight(), bitmap.getWidth(), bitmap.getHeight(), bitmap.getByteCount()));
        }

    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {

        // Читаем с inJustDecodeBounds=true для определения размеров
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Вычисляем inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,reqHeight);

        options.inJustDecodeBounds = false; // Читаем только с использованием inSampleSize коэффициента
        options.inPreferredConfig = Bitmap.Config.RGB_565; // Не будем читать прозрачные пиксели
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Реальные размеры изображения
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Вычисляем наибольший inSampleSize, который показывает во сколько раз загруженное изображение будет меньше оригинала.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
