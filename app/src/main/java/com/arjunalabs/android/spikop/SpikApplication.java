package com.arjunalabs.android.spikop;

import android.app.Application;

import com.arjunalabs.android.spikop.data.SpikRepository;
import com.arjunalabs.android.spikop.data.local.SpikLocalDataSource;
import com.arjunalabs.android.spikop.data.remote.SpikRemoteDataSource;

/**
 * Created by bobbyadiprabowo on 19/02/17.
 */

public class SpikApplication extends Application {

    private SpikLocalDataSource spikLocalDataSource;
    SpikRemoteDataSource spikRemoteDataSource;

    @Override
    public void onCreate() {
        super.onCreate();
        spikLocalDataSource = SpikLocalDataSource.getInstance(this);
        spikRemoteDataSource = SpikRemoteDataSource.getInstance(this);
    }

    public SpikRepository getSpikRepository() {
        return SpikRepository.getInstance(spikLocalDataSource, spikRemoteDataSource);
    }
}
