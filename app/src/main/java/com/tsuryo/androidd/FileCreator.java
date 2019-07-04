package com.tsuryo.androidd;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Tsur Yohananov on 2019-07-04.
 * <p>
 * Create txt files in a loop for the example
 */

class FileCreator {
    boolean generate(Context context,
                     String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(),
                    "AndroExecutor");
            if (!root.exists()) {
                root.mkdirs();
            }
            File file = new File(root, sFileName);
            FileWriter writer = new FileWriter(file);
            writer.append(sBody);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
