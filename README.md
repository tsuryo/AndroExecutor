[![Release](https://jitpack.io/v/tsuryo/AndroExecutor.svg)](https://jitpack.io/#tsuryo/AndroExecutor)

# AndroExecutor
Using ThreadPool is easy with AndroExecutor - Android library for easy multi-threading in Android.

# Features
* AndroExecutor is a android library that makes concurrency easy for you, using Executors.newCachedThreadPool(), Runnable, Handler.
* Easy to do heavy work concurrently.
* Implement Your Own BackgroundTask, UITask.
* Init AndroExecutor and run.

# Usage

Use:
```
void generateFiles() {
    for (int i = 0; i < 150; i++) {
        /**
         * Init the task that will run on the UI/Main Thread
         * Using UITask.class
         * */
        UITask uiTask = new UpdateTvTask(mTv, "Started");
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
	//Stop example
        if (i >= 50)
            mAndroExecutor.stopAll(); // stop the tasks and shutdown (may need to respond to interrupts)
    }
}
```
Implement your own BackgroundTask:
```
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
```
Implement your own UITask:
```
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
    implementation 'com.github.tsuryo:AndroExecutor:1.0'
}
```
