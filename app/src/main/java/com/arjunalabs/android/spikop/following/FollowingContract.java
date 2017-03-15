package com.arjunalabs.android.spikop.following;

import com.arjunalabs.android.spikop.BasePresenter;
import com.arjunalabs.android.spikop.BaseView;
import com.arjunalabs.android.spikop.addspik.SpikaddContract;
import com.arjunalabs.android.spikop.data.Hashtag;

import java.util.List;

/**
 * Created by bobbyadiprabowo on 14/03/17.
 */

public interface FollowingContract {

    interface View extends BaseView<FollowingContract.Presenter> {

        void setFollowingList(List<Hashtag> hashtags);

        void start();

        void resume();
    }

    interface Presenter extends BasePresenter {

        void fetchFollowingList();

    }
}
