package com.chevz.melapor.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chevz.melapor.data.model.Laporan;
import com.chevz.melapor.data.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "melapor.db";
    private static final int DATABASE_VERSION = 1;

    // Table User
    public static final String TABLE_USER = "users";
    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_NAMA = "nama";

    // Table Laporan
    public static final String TABLE_LAPORAN = "laporan";
    public static final String COL_JABATAN = "jabatan";
    public static final String COL_PERUSAHAAN = "perusahaan";
    public static final String COL_JENIS = "jenis";
    public static final String COL_KRONOLOGI = "kronologi";
    public static final String COL_FILEURL = "fileUrl";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUser = "CREATE TABLE " + TABLE_USER + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_PASSWORD + " TEXT, " +
                COL_NAMA + " TEXT)";
        db.execSQL(createUser);

        String createLaporan = "CREATE TABLE " + TABLE_LAPORAN + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAMA + " TEXT, " +
                COL_JABATAN + " TEXT, " +
                COL_PERUSAHAAN + " TEXT, " +
                COL_JENIS + " TEXT, " +
                COL_KRONOLOGI + " TEXT, " +
                COL_FILEURL + " TEXT)";
        db.execSQL(createLaporan);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAPORAN);
        onCreate(db);
    }

    // Register user
    public boolean insertUser(String username, String password, String nama, String level) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        values.put(COL_NAMA, nama);
        long result = db.insert(TABLE_USER, null, values);
        return result != -1;
    }

    // Login check
    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null,
                COL_USERNAME + "=? AND " + COL_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);
        return cursor != null && cursor.moveToFirst();
    }

    // Ambil nama berdasarkan username
    public String getNamaByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COL_NAMA},
                COL_USERNAME + "=?", new String[]{username}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        return "-";
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
        return result != -1;
    }
                                 }
