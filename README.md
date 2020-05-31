[![](https://jitpack.io/v/tsuryo/AndroExecutor.svg)](https://jitpack.io/#tsuryo/AndroExecutor)


# AndroExecutor
Using ThreadPool is easy with AndroExecutor - Android library for easy multi-threading in Android.

<img width="400" alt="Android multithreading" src="https://user-images.githubusercontent.com/42518244/83361339-f0df3700-a390-11ea-9f81-1cc6a10fe998.gif"> 

# Features
* AndroExecutor is an android library that makes concurrency easy for you, using Executors.newCachedThreadPool(), Runnable, Handler.
* Easy to do heavy work concurrently.
* Implement Your Own BackgroundTask, UITask.
* Init AndroExecutor and run.

# Usage

Use:
```Java
    private void generateFiles() {
        for (int i = 0; i < 150; i++) {
            TextView tv = new TextView(this);
            mLayout.addView(tv);
            /**
             * Init the task that will run on the UI/Main Thread
             * Using UITask.class
             * */
            UITask uiTask = new UpdateTvTask(tv, "Started");
            /**
             * Init the task that will run on a worker thread/ background
             * Using BackgroundTask.class
             * */
            BackgroundTask bgTask = new GenerateFileTask(this,
                    "file: " + i, uiTask);
            /**
             * Run in background
             * */
            mAndroExecutor.runOnWorker(bgTask);
        }
    }
```
Use 2:
```Java
UITask uiTask = new UITask() {
    @Override
    public void run() {
        pb.setVisibility(View.GONE);
    }
};
BackgroundTask backgroundTask = new BackgroundTask(uiTask) {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        AndroExecutor.getInstance().runOnUI(getUiTask());
    }
};
AndroExecutor.getInstance().runOnWorker(backgroundTask);
```

Implement your own BackgroundTask:
```Java
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
        //random thread sleep - for example purposes
        try {
            Thread.sleep(ThreadLocalRandom.current()
                    .nextInt(2500, 7000 + 1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (new FileGenerator().generate(mContext, mFileName, mFileName)) {
            /*
             * Update ui with new text
             * */
            UpdateTvTask tvTask = (UpdateTvTask) getUiTask();
            tvTask.setText("Done " + mFileName);
            AndroExecutor.getInstance().runOnUI(tvTask);
        }
    }
}
```
Implement your own UITask:
```Java
public class UpdateTvTask extends UITask {
    private final TextView mTv;
    private String mText;

    UpdateTvTask(TextView tv, String text) {
        mTv = tv;
        mText = text;
    }

    void setText(String mText) {
        this.mText = mText;
    }

    @Override
    public void run() {
        String text = mTv.getText().toString()
                + ", " + mText;
        mTv.setText(text);
    }
}
```

### Installing

Add the JitPack repository to your build file.
Add it in your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Add the dependency
```
dependencies {
	implementation 'com.github.tsuryo:AndroExecutor:1.1'
}
```
