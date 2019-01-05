package com.example.kozel.battleship;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DATABASE_NAME = "HighScores.db";
    private static final String TABLE_HIGH_SCORES = "HighScores_table";
    public static final String KEY_NAME = "NAME";
    public static final String KEY_SCORE = "SCORE";
    public static final String KEY_DIFFICULTY = "DIFFICULTY";

    private static DatabaseHelper instance = null;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    static DatabaseHelper getInstance(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_HIGH_SCORES_TABLE = "create table  " + TABLE_HIGH_SCORES + "("
                + KEY_NAME + " TEXT PRIMARY KEY," + KEY_SCORE + " INTEGER," + KEY_DIFFICULTY + " STRING" + ")";

        db.execSQL(CREATE_HIGH_SCORES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGH_SCORES);
        onCreate(db);
    }

    void insertData(String name, int score, String difficulty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_SCORE, score);
        contentValues.put(KEY_DIFFICULTY, difficulty);
        db.insert(TABLE_HIGH_SCORES, null, contentValues);
    }

    Cursor getAllData(String difficulty) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HIGH_SCORES +
                " WHERE " + KEY_DIFFICULTY + " = " + "\'" + difficulty + "\'" +
                " ORDER BY " + KEY_SCORE +
                " LIMIT 10", null);
    }
}