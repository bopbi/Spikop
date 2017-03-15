package com.arjunalabs.android.spikop.spiks;

import com.arjunalabs.android.spikop.BasePresenter;
import com.arjunalabs.android.spikop.BaseView;
import com.arjunalabs.android.spikop.data.Spik;

import java.util.List;

import rx.Observable;

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

        void loadLocalTimeline();

        boolean getTimelineFetchStatus();
    }
}
