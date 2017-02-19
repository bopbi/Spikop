package com.arjunalabs.android.spikop.data;

import java.util.List;

/**
 * Created by bobbyadiprabowo on 06/02/17.
 */

public class SpikRepository implements SpikDataSource {

    private static SpikRepository INSTANCE = null;

    private SpikDataSource localSpikDataSource;
    private SpikDataSource remoteSpikDataSource;

    private SpikRepository(SpikDataSource localSpikDataSource, SpikDataSource remoteSpikDataSource) {
        this.localSpikDataSource = localSpikDataSource;
        this.remoteSpikDataSource = remoteSpikDataSource;
    }

    public static SpikRepository getInstance(SpikDataSource localSpikDataSource, SpikDataSource remoteSpikDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new SpikRepository(localSpikDataSource, remoteSpikDataSource);
        }

        return INSTANCE;
    }

    @Override
    public List<Timeline> getAllSpiks() {
        return null;
    }

    @Override
    public Timeline getSpikById(long id) {
        return null;
    }
}
