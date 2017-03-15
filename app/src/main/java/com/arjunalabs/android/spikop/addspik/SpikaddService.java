package com.arjunalabs.android.spikop.addspik;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.arjunalabs.android.spikop.SpikApplication;
import com.arjunalabs.android.spikop.data.Spik;
import com.arjunalabs.android.spikop.data.SpikRepository;
import com.arjunalabs.android.spikop.spiks.SpiksService;
import com.arjunalabs.android.spikop.utils.Constant;

import java.util.PriorityQueue;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by bobbyadiprabowo on 28/02/17.
 */

public class SpikaddService extends Service {

    private SpikRepository spikRepository;
    private boolean isRunning = false;
    private PriorityQueue<String> stringQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        spikRepository = ((SpikApplication) getApplication()).getSpikRepository();
        stringQueue = new PriorityQueue<>();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        stringQueue.add(intent.getStringExtra(Constant.INTENT_VALUE_ADD_SPIK));
        if (!isRunning) {
            isRunning = true;
            postTimeline();
        }
        return Service.START_STICKY;
    }

    private void postTimeline() {

        Observable.from(stringQueue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        isRunning = false;
                        stopSelf();
                    }

                    @Override
                    public void onError(Throwable e) {
                        isRunning = false;
                        stopSelf();
                    }

                    @Override
                    public void onNext(String s) {
                        Spik spik = new Spik();
                        spik.setContent(stringQueue.peek());
                        spikRepository.addSpik(spik, false)
                                .subscribe(new Observer<Spik>() {
                                    @Override
                                    public void onCompleted() {
                                        Intent i = new Intent(Constant.INTENT_UPDATE_TIMELINE);
                                        LocalBroadcastManager.getInstance(SpikaddService.this).sendBroadcast(i);
                                        stringQueue.remove();
                                        isRunning = false;
                                        stopSelf();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Intent i = new Intent(Constant.INTENT_UPDATE_TIMELINE);
                                        LocalBroadcastManager.getInstance(SpikaddService.this).sendBroadcast(i);
                                        stringQueue.remove();
                                        isRunning = false;
                                        stopSelf();
                                    }

                                    @Override
                                    public void onNext(Spik spik) {
                                        Intent i = new Intent(Constant.INTENT_UPDATE_TIMELINE);
                                        LocalBroadcastManager.getInstance(SpikaddService.this).sendBroadcast(i);
                                        stringQueue.remove();
                                    }
                                });
                    }
                });

    }
}
