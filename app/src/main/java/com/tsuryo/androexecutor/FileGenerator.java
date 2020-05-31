package com.tsuryo.androexecutor;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Tsur Yohananov on 2019-07-04.
 * <p>
 * Create txt files in a loop for the example
 */

class FileGenerator {
    boolean generate(Context context,
                     String sFileName, String sBody) {
        try {
            File root = context.getExternalFilesDir("AndroExecutor");
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
