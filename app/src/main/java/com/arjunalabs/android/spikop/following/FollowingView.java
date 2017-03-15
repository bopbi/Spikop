package com.arjunalabs.android.spikop.following;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arjunalabs.android.spikop.R;
import com.arjunalabs.android.spikop.addspik.SpikaddContract;
import com.arjunalabs.android.spikop.data.Hashtag;
import com.arjunalabs.android.spikop.spiks.SpiksView;

import java.util.List;

/**
 * Created by bobbyadiprabowo on 14/03/17.
 */

public class FollowingView extends SwipeRefreshLayout implements FollowingContract.View {

    private FollowingAdapter followingAdapter;
    private RecyclerView followingRecyclerView;
    private FollowingContract.Presenter followingPresenter;
    private List<Hashtag> hashtags;

    public FollowingView(Context context) {
        super(context);
    }

    public FollowingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        followingRecyclerView = (RecyclerView) findViewById(R.id.following_recyclerview);
        followingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        followingAdapter = new FollowingAdapter();
        followingRecyclerView.setAdapter(followingAdapter);

        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRefreshing(true);
                followingPresenter.fetchFollowingList();
            }
        });
    }

    @Override
    public void setPresenter(FollowingContract.Presenter presenter) {
        followingPresenter = presenter;
    }

    @Override
    public void setFollowingList(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
        followingAdapter.notifyDataSetChanged();
        setRefreshing(false);
    }

    @Override
    public void start() {
        setRefreshing(true);
        followingPresenter.start();
    }

    @Override
    public void resume() {

    }

    class FollowingAdapter extends RecyclerView.Adapter<FollowingViewHolder> {

        @Override
        public FollowingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.item_following, parent, false);
            return new FollowingViewHolder(v);
        }

        @Override
        public void onBindViewHolder(FollowingViewHolder holder, int position) {
            Hashtag hashtag = hashtags.get(position);
            holder.textViewFollowingName.setText(hashtag.getName());
        }

        @Override
        public int getItemCount() {
            if (hashtags == null) {
                return 0;
            }

            return hashtags.size();
        }
    }

    class FollowingViewHolder extends RecyclerView.ViewHolder {

        TextView textViewFollowingName;

        public FollowingViewHolder(View itemView) {
            super(itemView);

            textViewFollowingName = (TextView) itemView.findViewById(R.id.item_following_textview_name);
        }
    }
}
