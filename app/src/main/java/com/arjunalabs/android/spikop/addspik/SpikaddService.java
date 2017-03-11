package com.arjunalabs.android.spikop.addspik;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.arjunalabs.android.spikop.SpikApplication;
import com.arjunalabs.android.spikop.data.Spik;
import com.arjunalabs.android.spikop.data.SpikRepository;
import com.arjunalabs.android.spikop.utils.Constant;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by bobbyadiprabowo on 28/02/17.
 */

public class SpikaddService extends Service {

    private SpikRepository spikRepository;
    private boolean isRunning = false;
    private LinkedBlockingDeque<String> stringQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        spikRepository = ((SpikApplication)getApplication()).getSpikRepository();
        stringQueue = new LinkedBlockingDeque<>();
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
        Thread postThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (stringQueue != null) {
                    Spik spik = new Spik();
                    spik.setContent(stringQueue.peek());
                    spikRepository.addSpik(spik);
                    stringQueue.poll();
                }
                isRunning = false;
                stopSelf();
            }
        });
        postThread.start();
    }
}
