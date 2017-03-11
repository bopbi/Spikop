package com.arjunalabs.android.spikop.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.arjunalabs.android.spikop.data.local.SpikDBHelper;

/**
 * Created by bobbyadiprabowo on 05/02/17.
 */

public class SpikopProvider extends ContentProvider {

    private SpikDBHelper dbHelper;

    // used for the UriMacher
    private static final int SPIKS = 10;
    private static final int SPIK_ID = 11;

    public static final String AUTHORITY = "com.arjunalabs.android.spikop.contentprovider";

    private static final String BASE_URI = "content://"+ AUTHORITY + "/";
    public static final String SPIKS_PATH = "spiks";
    public static final String SPIKS_URI = BASE_URI + SPIKS_PATH;


    // content provider routing
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, SPIKS_PATH + "/#", SPIK_ID);
        uriMatcher.addURI(AUTHORITY, SPIKS_PATH , SPIKS);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new SpikDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase sqLiteDatabase;
        Cursor cursor = null;
        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case SPIKS:
                sqLiteDatabase = dbHelper.getReadableDatabase();
                String query = "SELECT * FROM "+ SpikDBHelper.TABLE_SPIKS + " ORDER BY id DESC";
                cursor = sqLiteDatabase.rawQuery(query, null);
                break;
            case SPIK_ID:
                sqLiteDatabase = dbHelper.getReadableDatabase();
                query = "SELECT * FROM "+ SpikDBHelper.TABLE_SPIKS +" WHERE _id = ? ";
                cursor = sqLiteDatabase.rawQuery(query, new String[] {uri.getLastPathSegment()});
                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        SQLiteDatabase sqLiteDatabase;
        int count = 0;
        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case SPIKS:
                sqLiteDatabase = dbHelper.getWritableDatabase();
                sqLiteDatabase.beginTransaction();
                for (ContentValues contentValue : values) {
                    long newId = sqLiteDatabase.insert(SpikDBHelper.TABLE_SPIKS, null, contentValue);
                    if ( newId > 0) {
                        count++;
                    }
                }
                sqLiteDatabase.setTransactionSuccessful();
                sqLiteDatabase.endTransaction();
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase sqLiteDatabase;
        long id = 0;
        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case SPIKS:
                sqLiteDatabase = dbHelper.getWritableDatabase();
                id = sqLiteDatabase.insert(SpikDBHelper.TABLE_SPIKS, null, values);
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(SPIKS_URI + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase;
        int count = 0;
        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case SPIKS:
                sqLiteDatabase = dbHelper.getWritableDatabase();
                sqLiteDatabase.beginTransaction();
                count = sqLiteDatabase.delete(SpikDBHelper.TABLE_SPIKS, null, null);
                sqLiteDatabase.endTransaction();
                break;
            case SPIK_ID:

                break;
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // SQLiteDatabase sqLiteDatabase;
        // int count = 0;
        // TODO
        // return count;
        return 0;
    }
}
