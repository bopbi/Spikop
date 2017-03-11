package com.arjunalabs.android.spikop.spiks;

import com.arjunalabs.android.spikop.BasePresenter;
import com.arjunalabs.android.spikop.BaseView;
import com.arjunalabs.android.spikop.data.Spik;

import java.util.List;

/**
 * Created by bobbyadiprabowo on 04/02/17.
 */

public interface SpiksContract {

    interface View extends BaseView<Presenter> {

        void setSpiksList(List<Spik> spikList);

        void showAddSpik();

        void start();

        void resume();

        void runFetchService();
    }

    interface Presenter extends BasePresenter {

        void fetchTimeline();

        List<Spik> getTimeline();

        boolean getTimelineFetchStatus();
    }
}
