package com.zaleski.rafal.nameshaker.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseUtil {

    public static void copyDataBase(Context context, String dbName) throws IOException {
        InputStream myInput = context.getAssets().open(dbName);
        String outFileName = "/data/data/"
                + context.getApplicationContext().getPackageName()
                + "/databases/" + dbName;
        File file = new File(outFileName);

        Log.e("path", outFileName);
        if (file.exists())
            return;

        File outputFile = context.getDatabasePath(dbName + ".temp");
        outputFile.getParentFile().mkdirs();

        OutputStream myOutput = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);

        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
}
