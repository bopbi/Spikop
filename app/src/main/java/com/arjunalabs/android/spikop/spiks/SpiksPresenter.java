package com.arjunalabs.android.spikop.spiks;

import com.arjunalabs.android.spikop.data.Spik;
import com.arjunalabs.android.spikop.data.SpikRepository;
import com.arjunalabs.android.spikop.data.local.TransactionManager;

import java.util.List;

/**
 * Created by bobbyadiprabowo on 06/02/17.
 */

public class SpiksPresenter implements SpiksContract.Presenter {

    private SpikRepository spikRepository;
    private SpiksContract.View spikView;

    public SpiksPresenter(SpikRepository spikRepository, SpiksContract.View spikView) {
        this.spikRepository = spikRepository;
        this.spikView = spikView;
    }


    @Override
    public void start() {
        spikView.setSpiksList(getTimeline());
    }

    @Override
    public void end() {

    }

    @Override
    public void fetchTimeline() {
        spikView.runFetchService();
    }

    @Override
    public List<Spik> getTimeline() {
        return spikRepository.getAllSpiks(false, 0); // get all
    }

    @Override
    public boolean getTimelineFetchStatus() {
        return false;
    }
}
