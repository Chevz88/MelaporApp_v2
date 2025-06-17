package com.chevz.melapor.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chevz.melapor.data.model.Laporan;

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

    // Table Laporan
    private static final String TABLE_LAPORAN = "laporan";
    private static final String COL_JABATAN = "jabatan";
    private static final String COL_PERUSAHAAN = "perusahaan";
    private static final String COL_JENIS = "jenis";
    private static final String COL_KRONOLOGI = "kronologi";
    private static final String COL_FILEURL = "fileUrl";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT UNIQUE NOT NULL, " +
                COL_PASSWORD + " TEXT NOT NULL, " +
                COL_NAMA + " TEXT NOT NULL, " +
                COL_LEVEL + " TEXT DEFAULT 'User'" +
                ")";
        db.execSQL(createUsersTable);

        // Create Laporan table
        String createLaporanTable = "CREATE TABLE " + TABLE_LAPORAN + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAMA + " TEXT NOT NULL, " +
                COL_JABATAN + " TEXT, " +
                COL_PERUSAHAAN + " TEXT, " +
                COL_JENIS + " TEXT, " +
                COL_KRONOLOGI + " TEXT, " +
                COL_FILEURL + " TEXT)";
        db.execSQL(createLaporanTable);

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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAPORAN);
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

        boolean exists = (cursor != null && cursor.moveToFirst());
        if (cursor != null) cursor.close();
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

        boolean exists = (cursor != null && cursor.moveToFirst());
        if (cursor != null) cursor.close();
        db.close();
        return exists;
    }

    // Ambil nama user
    public String getNamaByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COL_NAMA},
                COL_USERNAME + "=?",
                new String[]{username},
                null, null, null);

        String nama = "-";
        if (cursor != null && cursor.moveToFirst()) {
            nama = cursor.getString(0);
            cursor.close();
        }
        db.close();
        return nama;
    }

    // Simpan laporan
    public boolean insertLaporan(Laporan laporan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAMA, laporan.getNama());
        values.put(COL_JABATAN, laporan.getJabatan());
        values.put(COL_PERUSAHAAN, laporan.getPerusahaan());
        values.put(COL_JENIS, laporan.getJenis());
        values.put(COL_KRONOLOGI, laporan.getKronologi());
        values.put(COL_FILEURL, laporan.getFileUrl());
        long result = db.insert(TABLE_LAPORAN, null, values);
        db.close();
        return result != -1;
    }
}
