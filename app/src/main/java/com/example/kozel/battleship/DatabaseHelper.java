package com.example.kozel.battleship;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Highscores.db";
    public static final String TABLE_NAME = "highscores_table";
    public static final String COL_1 = "NAME";
    public static final String COL_2 = "SCORE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table  " +TABLE_NAME+
                        "(NAME integer primary key, SCORE text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String score) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_2,score);
        long result= db.insert(TABLE_NAME,null,contentValues);
        if (result== -1)
            return false;
        else
            return true;
    }
}
