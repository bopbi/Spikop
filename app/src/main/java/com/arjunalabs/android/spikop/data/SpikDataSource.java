package com.arjunalabs.android.spikop.data;

import java.util.List;

import rx.Observable;

/**
 * Created by bobbyadiprabowo on 06/02/17.
 */

public interface SpikDataSource {

    Observable<List<Spik>> getAllSpiks(boolean refresh, long lastId);

    Observable<List<Hashtag>> getAllHashtags();

    Observable<Spik> getSpikById(long id);

    Observable<List<Spik>> addSpiks(List<Spik> spikList, boolean fromTimeline);

    Observable<Spik> addSpik(Spik spik, boolean fromRemote);
}
