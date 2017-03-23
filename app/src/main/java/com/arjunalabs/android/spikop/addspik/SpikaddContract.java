package com.arjunalabs.android.spikop.addspik;

import com.arjunalabs.android.spikop.BasePresenter;
import com.arjunalabs.android.spikop.BaseView;

/**
 * Created by bobbyadiprabowo on 12/02/17.
 */

public interface SpikaddContract {

    interface View extends BaseView<Presenter> {

        void onSendButtonClick();

        void startSendSpikService(String spik);

        void close();
    }

    interface Presenter extends BasePresenter {

        void sendSpik(String spik);

    }
}
