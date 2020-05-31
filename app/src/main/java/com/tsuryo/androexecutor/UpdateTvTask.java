package com.tsuryo.androexecutor;

import android.widget.TextView;

import com.tsuryo.androthread.UITask;

/**
 * Created by Tsur Yohananov on 2019-07-04.
 * <p>
 * UpdateTvTask is supposed to run on the main/ui thread
 */

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
