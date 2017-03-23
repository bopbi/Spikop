package com.arjunalabs.android.spikop.addspik;

import com.arjunalabs.android.spikop.data.SpikRepository;

/**
 * Created by bobbyadiprabowo on 12/02/17.
 */

public class SpikaddPresenter implements SpikaddContract.Presenter {

    private SpikaddView spikaddView;
    private SpikRepository spikRepository;

    public SpikaddPresenter(SpikRepository spikRepository, SpikaddView spikaddView) {
        this.spikRepository = spikRepository;
        this.spikaddView = spikaddView;
    }

    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    @Override
    public void sendSpik(String spik) {
        spikaddView.startSendSpikService(spik);
        spikaddView.close();
    }
}
