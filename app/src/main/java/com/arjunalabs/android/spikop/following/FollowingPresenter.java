package com.arjunalabs.android.spikop.following;

import com.arjunalabs.android.spikop.data.Hashtag;
import com.arjunalabs.android.spikop.data.SpikRepository;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by bobbyadiprabowo on 14/03/17.
 */

public class FollowingPresenter implements FollowingContract.Presenter {

    private SpikRepository spikRepository;
    private FollowingView followingView;

    public FollowingPresenter(SpikRepository spikRepository, FollowingView followingView) {
        this.spikRepository = spikRepository;
        this.followingView = followingView;
    }

    @Override
    public void start() {
        fetchFollowingList();
    }

    @Override
    public void end() {

    }

    @Override
    public void fetchFollowingList() {
        spikRepository.getFollowingHashtags()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Hashtag>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Hashtag> hashtags) {
                        followingView.setFollowingList(hashtags);
                    }
                });
    }
}
