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
import com.squareup.sqlbrite.BriteContentResolver;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bobbyadiprabowo on 06/02/17.
 */

public class SpikLocalDataSource implements SpikDataSource {

    private static SpikLocalDataSource INSTANCE = null;
    private Context context;
    private SqlBrite sqlBrite;

    private SpikLocalDataSource(Context context) {
        this.context = context;
        sqlBrite = new SqlBrite.Builder().build();
    }

    public static SpikLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
           INSTANCE = new SpikLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<Spik>> getAllSpiks(boolean refresh, long lastId) {

        BriteContentResolver resolver = sqlBrite.wrapContentProvider(context.getContentResolver(), Schedulers.io());
        return resolver
                .createQuery(Uri.parse(SpikopProvider.SPIKS_URI), null, null, null, null, false)
                .map(new Func1<SqlBrite.Query, List<Spik>>() {

            @Override
            public List<Spik> call(SqlBrite.Query query) {
                Cursor cursor = query.run();

                if (cursor == null) {
                    return null;
                }

                List<Spik> spikList = null;
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
                return spikList;
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
    public Observable<List<Spik>> addSpiks(final List<Spik> spikList, boolean fromTimeline) {

        return Observable.fromCallable(new Callable<List<Spik>>() {
            @Override
            public List<Spik> call() throws Exception {
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
        });

    }

    @Override
    public Observable<Spik> addSpik(final Spik spik, boolean fromTimeline) {

        return Observable.fromCallable(new Callable<Spik>() {

            @Override
            public Spik call() throws Exception {
                ContentValues cv = new ContentValues();
                cv.put(SpikDBHelper.COLUMN_REMOTE_ID, spik.getRemoteId());
                cv.put(SpikDBHelper.SPIKS_COLUMN_CONTENT, spik.getContent());
                cv.put(SpikDBHelper.COLUMN_CREATED_AT, spik.getCreatedAt());

                context.getContentResolver().insert(Uri.parse(SpikopProvider.SPIKS_URI),cv);
                return spik;
            }
        });

    }
}
