package com.tsuryo.androthread;

import android.os.Looper;

import java.util.concurrent.Executor;

import android.os.Handler;

/**
 * Created by Tsur Yohananov on 2019-07-04.
 * <p>
 * MainThreadExecutor uses Handler attached
 * to the main thread to post tasks on the main thread queue.
 */

public class MainThreadExecutor implements Executor {
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable runnable) {
        mHandler.post(runnable);
    }
}
