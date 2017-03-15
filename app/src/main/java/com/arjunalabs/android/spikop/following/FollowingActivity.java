package com.arjunalabs.android.spikop.following;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.arjunalabs.android.spikop.R;
import com.arjunalabs.android.spikop.SpikApplication;

/**
 * Created by bobbyadiprabowo on 14/03/17.
 */

public class FollowingActivity extends AppCompatActivity {

    private FollowingView followingView;
    private FollowingPresenter followingPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_following);

        setTitle(R.string.title_activity_following);

        followingView = (FollowingView) findViewById(R.id.following_view);
        followingPresenter = new FollowingPresenter(((SpikApplication)getApplication()).getSpikRepository(), followingView);

        followingView.setPresenter(followingPresenter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        followingView.start();
    }
}
