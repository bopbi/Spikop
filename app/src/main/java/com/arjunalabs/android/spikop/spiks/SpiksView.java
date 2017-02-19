package com.arjunalabs.android.spikop.spiks;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arjunalabs.android.spikop.R;
import com.arjunalabs.android.spikop.data.Timeline;

import java.util.List;

/**
 * Created by bobbyadiprabowo on 06/02/17.
 */

public class SpiksView extends RecyclerView implements SpiksContract.View {

    private SpiksContract.Presenter spiksPresenter;
    private SpiksAdapter spiksAdapter;
    private List<Timeline> timelineList;

    public SpiksView(Context context) {
        super(context);
        init();
    }

    public SpiksView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpiksView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        spiksAdapter = new SpiksAdapter();
        setAdapter(spiksAdapter);

        setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void setSpiksList(List<Timeline> timelineList) {
        this.timelineList = timelineList;
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
            Timeline timeline = timelineList.get(position);

            holder.spikContent.setText(timeline.getContent());
            holder.spikContentDate.setText(timeline.getCreatedAt());

        }

        @Override
        public int getItemCount() {
            if (timelineList == null) {
                return 0;
            }
            return timelineList.size();
        }
    }

    class SpiksViewHolder extends ViewHolder {

        TextView spikContent;
        TextView spikContentDate;

        public SpiksViewHolder(View itemView) {
            super(itemView);

            spikContent = (TextView) findViewById(R.id.item_spik_textview_content);
            spikContentDate = (TextView) findViewById(R.id.item_spik_textview_content_date);

        }
    }
}
