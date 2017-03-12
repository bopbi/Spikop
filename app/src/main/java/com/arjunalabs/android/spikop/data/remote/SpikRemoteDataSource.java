package com.arjunalabs.android.spikop.data.remote;

import android.content.Context;

import com.arjunalabs.android.spikop.data.Hashtag;
import com.arjunalabs.android.spikop.data.Spik;
import com.arjunalabs.android.spikop.data.SpikDataSource;
import com.arjunalabs.android.spikop.utils.NetworkAuthService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bobbyadiprabowo on 06/02/17.
 */

public class SpikRemoteDataSource implements SpikDataSource {

    private static SpikRemoteDataSource INSTANCE;
    private NetworkAuthService networkAuthService;


    private SpikRemoteDataSource(Context context) {
        networkAuthService = NetworkAuthService.getInstance(context);
    }

    public static SpikRemoteDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SpikRemoteDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<Spik>> getAllSpiks(boolean refresh, long lastId) {

        return networkAuthService
                .getSpikopService()
                .getTimeline(lastId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends SpikListResponseDTO>>() {
                    @Override
                    public Observable<? extends SpikListResponseDTO> call(Throwable throwable) {
                        return null;
                    }
                })
                .map(new Func1<SpikListResponseDTO, List<Spik>>() {

                    @Override
                    public List<Spik> call(SpikListResponseDTO spikListResponseDTO) {
                        List<Spik> spiks = new ArrayList<>(spikListResponseDTO.getSpiks().size());
                        for (SpikResponseDTO spikResponseDTO:
                                spikListResponseDTO.getSpiks()) {
                            Spik spik = new Spik(spikResponseDTO, true);
                            spiks.add(spik);
                        }
                        return spiks;
                    }
                });
    }

    @Override
    public Observable<List<Hashtag>> getAllHashtags() {
        return null;
    }

    @Override
    public Observable<Spik> getSpikById(long id) {
        return null;
    }

    @Override
    public Observable<List<Spik>> addSpiks(List<Spik> spikList, boolean fromTimeline) {
        return null;
    }

    @Override
    public Observable<Spik> addSpik(Spik spik, boolean fromTimeline) {
        return networkAuthService
                .getSpikopService()
                .addSpik(spik.getContent())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends SpikResponseDTO>>() {
                    @Override
                    public Observable<? extends SpikResponseDTO> call(Throwable throwable) {
                        return null;
                    }
                })
                .map(new Func1<SpikResponseDTO, Spik>() {

                    @Override
                    public Spik call(SpikResponseDTO spikResponseDTO) {
                        return new Spik(spikResponseDTO, false);
                    }
                });
    }
}
