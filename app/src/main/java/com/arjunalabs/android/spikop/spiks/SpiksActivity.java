package com.arjunalabs.android.spikop.spiks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.arjunalabs.android.spikop.R;
import com.arjunalabs.android.spikop.SpikApplication;
import com.arjunalabs.android.spikop.utils.Constant;

public class SpiksActivity extends AppCompatActivity {

    private SpiksPresenter spiksPresenter;
    private SpiksView spiksView;
    private SpikTimelineReceiver spikTimelineReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.title_activity_spiks);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spiksView = (SpiksView) findViewById(R.id.spiks_view);
        spiksPresenter = new SpiksPresenter(((SpikApplication)getApplication()).getSpikRepository(), spiksView);

        spiksView.setPresenter(spiksPresenter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spiksView.showAddSpik();
            }
        });

        spikTimelineReceiver = new SpikTimelineReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        spiksView.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        spiksView.resume();
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(spikTimelineReceiver, new IntentFilter(Constant.INTENT_UPDATE_TIMELINE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(spikTimelineReceiver);
    }

    class SpikTimelineReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            spiksView.start();
            spiksView.setRefreshing(false);
        }
    }
}
