package com.arjunalabs.android.spikop.spiks;

import android.content.Context;
import android.content.Intent;
import android.support.v4.util.TimeUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arjunalabs.android.spikop.R;
import com.arjunalabs.android.spikop.addspik.SpikaddActivity;
import com.arjunalabs.android.spikop.data.Spik;
import com.arjunalabs.android.spikop.data.local.TransactionManager;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.util.List;

/**
 * Created by bobbyadiprabowo on 06/02/17.
 */

public class SpiksView extends SwipeRefreshLayout implements SpiksContract.View {

    private SpiksContract.Presenter spiksPresenter;
    private SpiksAdapter spiksAdapter;
    private List<Spik> spikList;
    private RecyclerView recyclerView;

    public SpiksView(Context context) {
        super(context);
    }

    public SpiksView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.spiks_recyclerview);
        spiksAdapter = new SpiksAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(spiksAdapter);
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRefreshing(true);
                spiksPresenter.fetchTimeline();
            }
        });
    }

    @Override
    public void setSpiksList(List<Spik> spikList) {
        this.spikList = spikList;
        if (this.spikList != null) {
            spiksAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showAddSpik() {
        Intent spikaddIntent = new Intent(getContext(), SpikaddActivity.class);
        getContext().startActivity(spikaddIntent);
    }

    @Override
    public void start() {
        spiksPresenter.start();
    }

    @Override
    public void resume() {
        setRefreshing(TransactionManager.getTimelineTransactionStatus(getContext()));
    }

    @Override
    public void runFetchService() {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getContext()));
        Job myJob = dispatcher.newJobBuilder()
                // the JobService that will be called
                .setService(SpiksService.class)
                // uniquely identifies the job
                .setTag("spikservice")
                // one-off job
                .setRecurring(false)
                // don't persist past a device reboot
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                // start right away
                .setTrigger(Trigger.NOW)
                // don't overwrite an existing job with the same tag
                .setReplaceCurrent(false)
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                // constraints that need to be satisfied for the job to run
                .setConstraints(
                        // only run on an unmetered network
                        Constraint.ON_ANY_NETWORK
                )
                .build();

        dispatcher.mustSchedule(myJob);
    }

    @Override
    public void setPresenter(SpiksContract.Presenter presenter) {
        this.spiksPresenter = presenter;
    }

    class SpiksAdapter extends RecyclerView.Adapter<SpiksViewHolder> {

        @Override
        public SpiksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // refer https://www.bignerdranch.com/blog/understanding-androids-layoutinflater-inflate/
            View v = LayoutInflater.from(getContext()).inflate(R.layout.item_spik, parent, false);
            return new SpiksViewHolder(v);
        }

        @Override
        public void onBindViewHolder(SpiksViewHolder holder, int position) {
            Spik spik = spikList.get(position);

            holder.spikContent.setText(spik.getContent());
            holder.spikContentDate.setText(DateUtils.getRelativeTimeSpanString(spik.getCreatedAt()));

        }

        @Override
        public int getItemCount() {
            if (spikList == null) {
                return 0;
            }
            return spikList.size();
        }
    }

    class SpiksViewHolder extends RecyclerView.ViewHolder {

        TextView spikContent;
        TextView spikContentDate;

        public SpiksViewHolder(View itemView) {
            super(itemView);

            spikContent = (TextView) itemView.findViewById(R.id.item_spik_textview_content);
            spikContentDate = (TextView) itemView.findViewById(R.id.item_spik_textview_content_date);

        }
    }
}
