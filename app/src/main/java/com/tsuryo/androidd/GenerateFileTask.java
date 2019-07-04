package com.tsuryo.androidd;

import android.content.Context;

import com.tsuryo.androthread.AndroExecutor;
import com.tsuryo.androthread.BackgroundTask;
import com.tsuryo.androthread.UITask;

/**
 * Created by Tsur Yohananov on 2019-07-04.
 * <p>
 * GenerateFileTask is supposed to run on a background thread
 */

public class GenerateFileTask extends BackgroundTask {
    private final Context mContext;
    private final String mFileName;

    GenerateFileTask(Context context,
                     String fileName,
                     UITask uiTask) {
        super(uiTask);
        mContext = context;
        mFileName = fileName;
    }

    @Override
    public void run() {
        /*
         * update ui with the initialized ui task
         * */
        AndroExecutor.getInstance().runOnUI(getUiTask());
        if (new FileGenerator().generate(mContext, mFileName, mFileName)) {
            /*
             * Update ui with new text
             * */
            UpdateTvTask tvTask = (UpdateTvTask) getUiTask();
            tvTask.setText("Done");
            AndroExecutor.getInstance().runOnUI(tvTask);
        }
    }
}