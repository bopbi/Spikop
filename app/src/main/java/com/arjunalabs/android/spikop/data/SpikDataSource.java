package com.arjunalabs.android.spikop.data;

import java.util.List;

/**
 * Created by bobbyadiprabowo on 06/02/17.
 */

public interface SpikDataSource {

    List<Spik> getAllSpiks(boolean refresh, long lastId);

    List<Hashtag> getAllHashtags();

    Spik getSpikById(long id);

    long addSpiks(List<Spik> spikList);

    long addSpik(Spik spik);
}
