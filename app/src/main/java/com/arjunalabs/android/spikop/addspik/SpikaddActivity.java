package com.arjunalabs.android.spikop.addspik;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.arjunalabs.android.spikop.R;
import com.arjunalabs.android.spikop.SpikApplication;

/**
 * Created by bobbyadiprabowo on 06/02/17.
 */

public class SpikaddActivity extends AppCompatActivity {

    private SpikaddView spikaddView;
    private SpikaddPresenter spikaddPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);

        setTitle(R.string.title_activity_spikadd);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spikaddView = (SpikaddView) findViewById(R.id.spikadd_view);
        spikaddPresenter = new SpikaddPresenter(((SpikApplication)getApplication()).getSpikRepository(), spikaddView);
        spikaddView.setPresenter(spikaddPresenter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
