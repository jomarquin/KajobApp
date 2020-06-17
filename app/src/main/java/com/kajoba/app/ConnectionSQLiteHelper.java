package com.kajoba.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kajoba.app.Utilities.Utilities;

import androidx.annotation.Nullable;

public class ConnectionSQLiteHelper extends SQLiteOpenHelper {


    public ConnectionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilities.CREATE_TABLE_CLIENT);
        db.execSQL(Utilities.CREATE_TABLE_BUSINESSMAN);
        db.execSQL(Utilities.CREATE_TABLE_PARTNER);
        db.execSQL(Utilities.CREATE_TABLE_PRODUCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_CLIENT);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_BUSINESSMAN);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_PARTNER);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_PRODUCT);
        onCreate(db);
    }
}
