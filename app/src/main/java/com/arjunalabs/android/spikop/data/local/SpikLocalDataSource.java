package com.arjunalabs.android.spikop.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;

import com.arjunalabs.android.spikop.data.Hashtag;
import com.arjunalabs.android.spikop.data.Spik;
import com.arjunalabs.android.spikop.data.SpikDataSource;
import com.arjunalabs.android.spikop.provider.SpikopProvider;
import com.arjunalabs.android.spikop.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobbyadiprabowo on 06/02/17.
 */

public class SpikLocalDataSource implements SpikDataSource {

    private static SpikLocalDataSource INSTANCE = null;
    private Context context;

    private SpikLocalDataSource(Context context) {
        this.context = context;
    }

    public static SpikLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
           INSTANCE = new SpikLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public List<Spik> getAllSpiks(boolean refresh, long lastId) {
        List<Spik> spikList = null;
        Cursor cursor = context.getContentResolver().query(Uri.parse(SpikopProvider.SPIKS_URI), null, null, null, null);
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToFirst()) {
            int colId = cursor.getColumnIndex(SpikDBHelper.COLUMN_ID);
            int colContent = cursor.getColumnIndex(SpikDBHelper.SPIKS_COLUMN_CONTENT);
            int colCreatedAt = cursor.getColumnIndex(SpikDBHelper.COLUMN_CREATED_AT);
            int colRemoteId = cursor.getColumnIndex(SpikDBHelper.COLUMN_REMOTE_ID);
            spikList = new ArrayList<>(cursor.getCount());

            // first cursor
            Spik mSpik = new Spik();
            mSpik.setId(cursor.getLong(colId));
            mSpik.setContent(cursor.getString(colContent));
            mSpik.setCreatedAt(cursor.getLong(colCreatedAt));
            mSpik.setRemoteId(cursor.getLong(colRemoteId));
            spikList.add(mSpik);

            // others
            while (cursor.moveToNext()) {
                Spik spik = new Spik();
                spik.setId(cursor.getLong(colId));
                spik.setRemoteId(cursor.getLong(colRemoteId));
                spik.setContent(cursor.getString(colContent));
                spik.setCreatedAt(cursor.getLong(colCreatedAt));
                spikList.add(spik);
            }
        }
        cursor.close();
        if (spikList != null) {
            return spikList;
        }
        return null;
    }

    @Override
    public List<Hashtag> getAllHashtags() {
        return null;
    }

    @Override
    public Spik getSpikById(long id) {
        return null;
    }

    @Override
    public List<Spik> addSpiks(List<Spik> spikList) {
        List<ContentValues> contentValues = new ArrayList<>(spikList.size());

        for (Spik spik : spikList) {
            ContentValues cv = new ContentValues();
            cv.put(SpikDBHelper.COLUMN_REMOTE_ID, spik.getRemoteId());
            cv.put(SpikDBHelper.SPIKS_COLUMN_CONTENT, spik.getContent());
            cv.put(SpikDBHelper.COLUMN_CREATED_AT, spik.getCreatedAt());
            contentValues.add(cv);
        }
        context.getContentResolver().bulkInsert(Uri.parse(SpikopProvider.SPIKS_URI), contentValues.toArray(new ContentValues[spikList.size()]));

        return spikList;
    }

    @Override
    public Spik addSpik(Spik spik) {
        ContentValues cv = new ContentValues();
        cv.put(SpikDBHelper.COLUMN_REMOTE_ID, spik.getRemoteId());
        cv.put(SpikDBHelper.SPIKS_COLUMN_CONTENT, spik.getContent());
        cv.put(SpikDBHelper.COLUMN_CREATED_AT, spik.getCreatedAt());

        context.getContentResolver().insert(Uri.parse(SpikopProvider.SPIKS_URI),cv);
        return spik;
    }
}
