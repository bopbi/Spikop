package com.arjunalabs.android.spikop.spiks;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.arjunalabs.android.spikop.SpikApplication;
import com.arjunalabs.android.spikop.data.Spik;
import com.arjunalabs.android.spikop.data.SpikRepository;
import com.arjunalabs.android.spikop.data.local.TransactionManager;
import com.arjunalabs.android.spikop.utils.Constant;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by bobbyadiprabowo on 19/02/17.
 */

public class SpiksService extends Service {

    private SpikRepository spikRepository;
    private boolean isRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();
        spikRepository = ((SpikApplication)getApplication()).getSpikRepository();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (!isRunning) {
            isRunning = true;
            fetchTimeline();
        }
        return Service.START_STICKY;
    }

    private void fetchTimeline() {
        Observable.fromCallable(new Func0<Void>() {
            @Override
            public Void call() {
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

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Spik> spiks) {
                        TransactionManager.saveTimelineTransactionStatus(SpiksService.this, false);
                        if (spiks != null) {
                            TransactionManager.saveTimelineLastRemoteId(SpiksService.this, spiks.get(0).getRemoteId());
                        }

                        LocalBroadcastManager.getInstance(SpiksService.this).sendBroadcast(i);
                        isRunning = false;
                        stopSelf();
                    }
                });
                return null;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
