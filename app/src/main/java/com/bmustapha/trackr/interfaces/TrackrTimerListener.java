package com.bmustapha.trackr.interfaces;

/**
 * Created by andela on 10/16/15.
 */
public interface TrackrTimerListener {
    void onInterval(long millis);
    void finished();
}
