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
    public List<Spik> getAllSpiks(boolean refresh, long lastId) {
        List<Spik> spikList;
        if (refresh) {
            spikList = remoteSpikDataSource.getAllSpiks(refresh, lastId);
            if (spikList == null) {
                return null;
            }
            localSpikDataSource.addSpiks(spikList);
            spikList = localSpikDataSource.getAllSpiks(refresh, lastId);
        } else {
            spikList = localSpikDataSource.getAllSpiks(refresh, lastId);
        }
        return spikList;
    }

    @Override
    public List<Hashtag> getAllHashtags() {
        return null;
    }

    @Override
    public Spik getSpikById(long id) {
        return null;
    }

    @Override
    public long addSpiks(List<Spik> spikList) {
        return 0;
    }

    @Override
    public long addSpik(Spik spik) {
        remoteSpikDataSource.addSpik(spik);
        localSpikDataSource.addSpik(spik);
        return 1;
    }
}
