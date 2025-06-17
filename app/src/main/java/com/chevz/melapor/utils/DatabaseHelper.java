package com.chevz.melapor.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    
    private static final String DATABASE_NAME = "MelaporApp.db";
    private static final int DATABASE_VERSION = 1;
    
    // Table Users
    private static final String TABLE_USERS = "users";
    private static final String COL_ID = "id";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final String COL_NAMA = "nama";
    private static final String COL_LEVEL = "level";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT UNIQUE NOT NULL, " +
                COL_PASSWORD + " TEXT NOT NULL, " +
                COL_NAMA + " TEXT NOT NULL, " +
                COL_LEVEL + " TEXT DEFAULT 'User'" +
                ")";
        db.execSQL(createUsersTable);
        
        // Insert default admin user
        ContentValues adminValues = new ContentValues();
        adminValues.put(COL_USERNAME, "admin");
        adminValues.put(COL_PASSWORD, "admin123");
        adminValues.put(COL_NAMA, "Administrator");
        adminValues.put(COL_LEVEL, "Admin");
        db.insert(TABLE_USERS, null, adminValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Insert new user
    public boolean insertUser(String username, String password, String nama, String level) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        values.put(COL_NAMA, nama);
        values.put(COL_LEVEL, level);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    // Check user credentials
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COL_ID},
                COL_USERNAME + "=? AND " + COL_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // Check if username exists
    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COL_ID},
                COL_USERNAME + "=?",
                new String[]{username},
                null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
}
