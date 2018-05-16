package com.zaleski.rafal.nameshaker.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class BitmapUtil {

    public static Bitmap decodeWithScaling(String pathName) {
        int targetW = 355;
        int targetH = 461;

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        return BitmapFactory.decodeFile(pathName, bmOptions);
    }

    public static Bitmap setTextOnBitmap(Bitmap bitmap, String text) {
        Bitmap bmp = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas c = new Canvas(bmp);
        Paint p = new Paint();
        p.setTypeface(Typeface.DEFAULT);
        p.setTextSize(20);
        p.setColor(Color.YELLOW);
        int width = (int) p.measureText(text);
        int yPos = (int) ((c.getHeight() - 20)
                - ((p.descent() + p.ascent()) / 2) - 10);
        c.drawText(text, (bmp.getWidth() - width) / 2, yPos, p);
        return bmp;
    }

}
