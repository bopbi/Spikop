package com.arjunalabs.android.spikop.addspik;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.arjunalabs.android.spikop.R;
import com.arjunalabs.android.spikop.utils.Constant;

/**
 * Created by bobbyadiprabowo on 12/02/17.
 */

public class SpikaddView extends LinearLayout implements SpikaddContract.View {

    private EditText editTextSpik;
    private Button buttonSendSpik;
    private SpikaddContract.Presenter presenter;

    public SpikaddView(Context context) {
        super(context);
    }

    public SpikaddView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpikaddView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        editTextSpik = (EditText) findViewById(R.id.edit_spik);
        buttonSendSpik = (Button) findViewById(R.id.button_submit);

        buttonSendSpik.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendButtonClick();
            }
        });
    }

    @Override
    public void setPresenter(SpikaddContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onSendButtonClick() {
        presenter.sendSpik(editTextSpik.getText().toString());
    }

    @Override
    public void startSendSpikService(String spik) {
        Intent addSpikIntent = new Intent(getContext(), SpikaddService.class);
        addSpikIntent.putExtra(Constant.INTENT_VALUE_ADD_SPIK, editTextSpik.getText().toString());
        getContext().startService(addSpikIntent);
    }

    @Override
    public void close() {
        if (getContext() instanceof Activity) {
            ((Activity)getContext()).finish();
        }
    }
}
