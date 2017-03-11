package com.arjunalabs.android.spikop.addspik;

import com.arjunalabs.android.spikop.BasePresenter;
import com.arjunalabs.android.spikop.BaseView;

/**
 * Created by bobbyadiprabowo on 12/02/17.
 */

public interface SpikaddContract {

    interface View extends BaseView<Presenter> {

        void onSendButtonClick();

    }

    interface Presenter extends BasePresenter {


    }
}
