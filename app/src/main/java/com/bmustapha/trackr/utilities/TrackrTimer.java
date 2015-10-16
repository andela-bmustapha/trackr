package com.bmustapha.trackr.utilities;

import android.os.CountDownTimer;

import com.bmustapha.trackr.interfaces.TrackrTimerListener;

/**
 * Created by tunde on 10/16/15.
 */
public class TrackrTimer extends CountDownTimer {

    private TrackrTimerListener trackrTimerListener;

    public TrackrTimer(long millisInFuture, long countDownInterval, TrackrTimerListener trackrTimerListener) {
        super(millisInFuture, countDownInterval);
        this.trackrTimerListener = trackrTimerListener;
    }

    @Override
    public void onTick(long millisUntilFinish) {
        trackrTimerListener.onInterval(millisUntilFinish);
    }

    @Override
    public void onFinish() {
        trackrTimerListener.finished();
    }
}
