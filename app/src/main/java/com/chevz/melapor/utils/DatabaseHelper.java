package com.chevz.melapor.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import com.chevz.melapor.data.model.User;
import com.chevz.melapor.data.model.Laporan;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "melapor.db";
    private static final int DATABASE_VERSION = 1;

    // Table User
    private static final String TABLE_USER = "users";
    private static final String COL_USER_ID = "id";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final String COL_NAMA = "nama";

    // Table Laporan
    private static final String TABLE_LAPORAN = "laporan";
    private static final String COL_LAPORAN_ID = "id";
    private static final String COL_NAMA_USER = "nama";
    private static final String COL_JABATAN = "jabatan";
    private static final String COL_PERUSAHAAN = "perusahaan";
    private static final String COL_JENIS = "jenis";
    private static final String COL_KRONOLOGI = "kronologi";
    private static final String COL_FILE_URI = "fileUri";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TABLE_USER + " ("
                + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_USERNAME + " TEXT, "
                + COL_PASSWORD + " TEXT, "
                + COL_NAMA + " TEXT)";

        String createLaporanTable = "CREATE TABLE " + TABLE_LAPORAN + " ("
                + COL_LAPORAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAMA_USER + " TEXT, "
                + COL_JABATAN + " TEXT, "
                + COL_PERUSAHAAN + " TEXT, "
                + COL_JENIS + " TEXT, "
                + COL_KRONOLOGI + " TEXT, "
                + COL_FILE_URI + " TEXT)";

        db.execSQL(createUserTable);
        db.execSQL(createLaporanTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAPORAN);
        onCreate(db);
    }

    // Register
    public boolean insertUser(String username, String password, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        values.put(COL_NAMA, nama);
        long result = db.insert(TABLE_USER, null, values);
        return result != -1;
    }

    // Login
    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null,
                COL_USERNAME + "=? AND " + COL_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);
        boolean result = (cursor != null && cursor.moveToFirst());
        if (cursor != null) cursor.close();
        return result;
    }

    // Insert Laporan
    public boolean insertLaporan(Laporan laporan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAMA_USER, laporan.getNama());
        values.put(COL_JABATAN, laporan.getJabatan());
        values.put(COL_PERUSAHAAN, laporan.getPerusahaan());
        values.put(COL_JENIS, laporan.getJenis());
        values.put(COL_KRONOLOGI, laporan.getKronologi());
        values.put(COL_FILE_URI, laporan.getFileUri());
        long result = db.insert(TABLE_LAPORAN, null, values);
        return result != -1;
    }
}
