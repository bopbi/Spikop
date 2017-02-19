package com.arjunalabs.android.spikop.spiks;

import com.arjunalabs.android.spikop.BasePresenter;
import com.arjunalabs.android.spikop.BaseView;
import com.arjunalabs.android.spikop.data.Timeline;

import java.util.List;

/**
 * Created by bobbyadiprabowo on 04/02/17.
 */

public interface SpiksContract {

    interface View extends BaseView<Presenter> {

        void setSpiksList(List<Timeline> timelineList);

    }

    interface Presenter extends BasePresenter {

        void showAddSpik();

    }
}
