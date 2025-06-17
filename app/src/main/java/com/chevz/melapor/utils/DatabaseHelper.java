package com.chevz.melapor.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "melapor.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_USER = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_LEVEL = "level";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT UNIQUE,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_NAMA + " TEXT,"
                + COLUMN_LEVEL + " TEXT" + ");";

        db.execSQL(CREATE_USER_TABLE);

        // Admin default (opsional)
        db.execSQL("INSERT INTO " + TABLE_USER + " ("
                + COLUMN_USERNAME + ", "
                + COLUMN_PASSWORD + ", "
                + COLUMN_NAMA + ", "
                + COLUMN_LEVEL + ") VALUES ('admin', 'admin123', 'Administrator', 'admin')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
}
