package com.tsuryo.androidd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsuryo.android.R;
import com.tsuryo.androthread.AndroExecutor;
import com.tsuryo.androthread.BackgroundTask;
import com.tsuryo.androthread.UITask;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    private AndroExecutor mAndroExecutor;
    private Button mButton;
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUiComponents();

        /*
         *
         * init AndroExecutor
         * */
        mAndroExecutor = AndroExecutor.getInstance();
        askForPermissions();
    }

    private void initUiComponents() {
        mLayout = findViewById(R.id.layout);
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(this);
    }

    private void askForPermissions() {
        String[] permissionsToAsk = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        for (String p : permissionsToAsk) {
            boolean isGranted = ContextCompat
                    .checkSelfPermission(this, p)
                    == PackageManager.PERMISSION_GRANTED;
            if (!isGranted)
                ActivityCompat.requestPermissions(this,
                        new String[]{p}, 0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button)
            generateFiles();
    }
}
