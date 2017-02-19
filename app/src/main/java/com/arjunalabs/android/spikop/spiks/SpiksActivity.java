package com.arjunalabs.android.spikop.spiks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.arjunalabs.android.spikop.R;

public class SpiksActivity extends AppCompatActivity {

    private SpiksPresenter spiksPresenter;
    private SpiksView spiksView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spiksPresenter = new SpiksPresenter();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spiksPresenter.showAddSpik();

            }
        });

        spiksView = (SpiksView) findViewById(R.id.spiks_view);
        spiksView.setPresenter(spiksPresenter);
    }
}
