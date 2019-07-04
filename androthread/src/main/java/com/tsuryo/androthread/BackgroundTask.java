package com.tsuryo.androthread;

/**
 * Created by Tsur Yohananov on 2019-07-04.
 */

public abstract class BackgroundTask implements Runnable {
    private final UITask mUiTask;

    public BackgroundTask(UITask uiTask) {
        mUiTask = uiTask;
    }

    protected UITask getUiTask() {
        return mUiTask;
    }
}
