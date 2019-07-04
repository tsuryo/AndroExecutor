package com.tsuryo.androthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

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
        try {
            mService.execute(runnable);
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
        }

    }

    public void runOnUI(Runnable runnable) {
        mMainThreadExecutor.execute(runnable);
    }

    /**
     * Call stopAll to stop all running tasks and shut down the ExecutorService
     * ATTENTION: (if necessary, while/for loops)
     * make sure to try{} catch{InterruptedException e}
     * in your tasks and release resources if needed.
     * <p>
     * will cancel via Thread.interrupt, so any task that fails to respond to
     * interrupts may never terminate.
     */
    public void stopAll() {
        mService.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!mService.awaitTermination(30, TimeUnit.SECONDS)) {
                mService.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!mService.awaitTermination(30, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            mService.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }


    }
}
