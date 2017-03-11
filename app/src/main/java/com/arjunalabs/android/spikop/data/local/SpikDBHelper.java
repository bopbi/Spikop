package com.arjunalabs.android.spikop.data.local;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bobbyadiprabowo on 05/02/17.
 */

public class SpikDBHelper extends SQLiteOpenHelper {

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_REMOTE_ID = "id";
    public static final String COLUMN_CREATED_AT = "created_at";

    public static final String TABLE_SPIKS = "spiks";
    public static final String SPIKS_COLUMN_CONTENT = "content";

    public static final String TABLE_HASHTAGS = "hashtags";
    public static final String HASHTAGS_COLUMN_NAME = "name";

    private static final String DATABASE_NAME = "spikop.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String CREATE_SPIKS = "create table "
            + TABLE_SPIKS + "( " + COLUMN_ID  + " integer primary key autoincrement, "
            + "" + COLUMN_REMOTE_ID + " integer not null, "
            + "" + SPIKS_COLUMN_CONTENT + " text not null, "
            + "" + COLUMN_CREATED_AT + " INTEGER NOT NULL DEFAULT (strftime('%s','now'))"
            + ");";

    private static final String CREATE_HASHTAGS = "create table "
            + TABLE_HASHTAGS + "( " + COLUMN_ID  + " integer primary key autoincrement, "
            + "" + COLUMN_REMOTE_ID + " integer not null, "
            + "" + HASHTAGS_COLUMN_NAME + " text not null, "
            + "" + COLUMN_CREATED_AT + " INTEGER NOT NULL DEFAULT (strftime('%s','now'))"
            + ");";

    public SpikDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, new DatabaseErrorHandler() {
            @Override
            public void onCorruption(SQLiteDatabase dbObj) {

            }
        });
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SPIKS);
        db.execSQL(CREATE_HASHTAGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
