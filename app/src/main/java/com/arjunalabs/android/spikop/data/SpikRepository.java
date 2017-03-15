package com.arjunalabs.android.spikop.data;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by bobbyadiprabowo on 06/02/17.
 */

public class SpikRepository implements SpikDataSource {

    private static SpikRepository INSTANCE = null;

    private SpikDataSource localSpikDataSource;
    private SpikDataSource remoteSpikDataSource;

    private SpikRepository(SpikDataSource localSpikDataSource, SpikDataSource remoteSpikDataSource) {
        this.localSpikDataSource = localSpikDataSource;
        this.remoteSpikDataSource = remoteSpikDataSource;
    }

    public static SpikRepository getInstance(SpikDataSource localSpikDataSource, SpikDataSource remoteSpikDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new SpikRepository(localSpikDataSource, remoteSpikDataSource);
        }

        return INSTANCE;
    }

    @Override
    public Observable<List<Spik>> getAllSpiks(final boolean refresh, final long lastId) {
        if (refresh) {
            return remoteSpikDataSource
                    .getAllSpiks(refresh, lastId)
                    .switchMap(new Func1<List<Spik>, Observable<List<Spik>>>() {
                        @Override
                        public Observable<List<Spik>> call(List<Spik> spiks) {
                            return localSpikDataSource.addSpiks(spiks, true);
                        }
                    })
                    .switchMap(new Func1<List<Spik>, Observable<List<Spik>>>() {
                        @Override
                        public Observable<List<Spik>> call(List<Spik> spiks) {
                            return localSpikDataSource.getAllSpiks(refresh, lastId);
                        }
                    });
        } else {
            return localSpikDataSource.getAllSpiks(refresh, lastId);
        }
    }

    @Override
    public Observable<List<Hashtag>> getAllHashtags() {
        return null;
    }

    @Override
    public Observable<List<Hashtag>> getFollowingHashtags() {
        return remoteSpikDataSource.getFollowingHashtags();
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
    public Observable<Spik> addSpik(Spik spik, final boolean fromTimeline) {
        return remoteSpikDataSource.addSpik(spik, fromTimeline)
                .switchMap(new Func1<Spik, Observable<Spik>>() {
            @Override
            public Observable<Spik> call(Spik newSpik) {
                return localSpikDataSource.addSpik(newSpik, fromTimeline);
            }
        });
    }
}
