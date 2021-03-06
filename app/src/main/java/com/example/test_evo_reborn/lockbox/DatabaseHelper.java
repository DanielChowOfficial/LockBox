package com.example.test_evo_reborn.lockbox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by test-evo-REBORN on 2/28/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "LockBox.db";
    public static final String TABLE_NAME = "lock_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "COMBO";
    SQLiteDatabase db;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_1 + " INTEGER PRIMARY KEY," +
                COL_2 + " TEXT," +
                COL_3 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void addLock(String name, String combo, String id){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        System.out.println("lock " + name + "added.");
        values.put(COL_1, id);
        values.put(COL_2, name);
        values.put(COL_3, combo);
        db.insert(TABLE_NAME, null, values);
    }
    public void deleteLock(String id){
        db = this.getWritableDatabase();
        System.out.println(db.delete(TABLE_NAME, "ID = ?", new String[]{id}) + " deleted");

    }

    public Cursor getAllLocks(){
        db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME, null);
        return result;
    }
}
