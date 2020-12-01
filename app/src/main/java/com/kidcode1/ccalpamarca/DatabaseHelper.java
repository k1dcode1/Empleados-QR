package com.kidcode1.ccalpamarca;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="IDU.db";
    public static final String TABLE_NAME="IDU";
    //COLs
    public static final String COLS_1="ID";
    public static final String COLS_2="name";
    public static final String COLS_3="address";
    public static final String COLS_4="phone";
    public static final String COLS_5="email";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +TABLE_NAME+ "(ID INTEGER PRIMARY KEY, name TEXT, address TEXT, phone TEXT, email TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
}
