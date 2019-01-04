package com.example.kozel.battleship;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DATABASE_NAME = "HighScores.db";
    private static final String TABLE_HIGH_SCORES = "HighScores_table";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_SCORE = "SCORE";

    private static DatabaseHelper instance= null;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    public static DatabaseHelper getInstance(Context context){
     if (instance==null)
         instance=new DatabaseHelper(context);
     return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_HIGH_SCORES_TABLE = "create table  " + TABLE_HIGH_SCORES + "("
                + KEY_NAME + " TEXT PRIMARY KEY," + KEY_SCORE + " INTEGER" + ")";

        db.execSQL(CREATE_HIGH_SCORES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGH_SCORES);
        onCreate(db);
    }

    public void insertData(String name,int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_SCORE, score);
        db.insert(TABLE_HIGH_SCORES, null, contentValues);
    }
}