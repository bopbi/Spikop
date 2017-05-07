package com.arjunalabs.android.spikop.spiks;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.arjunalabs.android.spikop.SpikApplication;
import com.arjunalabs.android.spikop.data.Spik;
import com.arjunalabs.android.spikop.data.SpikRepository;
import com.arjunalabs.android.spikop.data.local.TransactionManager;
import com.arjunalabs.android.spikop.utils.Constant;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by bobbyadiprabowo on 19/02/17.
 */

public class SpiksService extends JobService {

    private SpikRepository spikRepository;
    private boolean isRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();
        spikRepository = ((SpikApplication) getApplication()).getSpikRepository();
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        if (!isRunning) {
            isRunning = true;
            fetchTimeline();
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

    private void fetchTimeline() {
        TransactionManager.saveTimelineTransactionStatus(SpiksService.this, true);
        final Intent i = new Intent(Constant.INTENT_UPDATE_TIMELINE);

        LocalBroadcastManager.getInstance(SpiksService.this).sendBroadcast(i);

        long lastId = TransactionManager.getTimelineLastRemoteId(SpiksService.this);
        spikRepository.getAllSpiks(true, lastId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Spik>>() {
                    @Override
                    public void onCompleted() {
                        TransactionManager.saveTimelineTransactionStatus(SpiksService.this, false);
                        LocalBroadcastManager.getInstance(SpiksService.this).sendBroadcast(i);
                        isRunning = false;
                        stopSelf();
                    }

                    @Override
                    public void onError(Throwable e) {
                        TransactionManager.saveTimelineTransactionStatus(SpiksService.this, false);
                        LocalBroadcastManager.getInstance(SpiksService.this).sendBroadcast(i);
                        isRunning = false;
                        stopSelf();
                    }

                    @Override
                    public void onNext(List<Spik> spiks) {
                        TransactionManager.saveTimelineTransactionStatus(SpiksService.this, false);
                        if (spiks != null) {
                            TransactionManager.saveTimelineLastRemoteId(SpiksService.this, spiks.get(0).getRemoteId());
                        }

                        LocalBroadcastManager.getInstance(SpiksService.this).sendBroadcast(i);
                    }
                });
    }
}
