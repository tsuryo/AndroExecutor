package com.tsuryo.androthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Tsur Yohananov on 2019-07-04.
 */

public class AndroExecutor {
    private static AndroExecutor mInstance;
    private static MainThreadExecutor mMainThreadExecutor;
    private ExecutorService mService;

    static {
        mInstance = new AndroExecutor();
        mMainThreadExecutor = new MainThreadExecutor();
    }

    private AndroExecutor() {
        mService = Executors.newCachedThreadPool();
    }

    private MainThreadExecutor getMainThreadExecutor() {
        return mMainThreadExecutor;
    }

    public static AndroExecutor getInstance() {
        return mInstance;
    }

    public void runOnWorker(Runnable runnable) {
        mService.execute(runnable);
    }

    public void runOnUI(Runnable runnable) {
        mMainThreadExecutor.execute(runnable);
    }
}
