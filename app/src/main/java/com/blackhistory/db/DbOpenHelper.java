package com.blackhistory.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenHelper {
    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(DataBases.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+DataBases.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context){
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }


    public long insertColumn(String name, String number, String memo, String createdat){
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.NAME, name);
        values.put(DataBases.CreateDB.NUMBER, number);
        values.put(DataBases.CreateDB.MEMO, memo);
        values.put(DataBases.CreateDB.CREATEDAT, createdat);
        values.put(DataBases.CreateDB.COUNT, "0");
        return mDB.insert(DataBases.CreateDB._TABLENAME0, null, values);
    }

    public Cursor selectColumns(){
        return mDB.query(DataBases.CreateDB._TABLENAME0, null, null, null, null, null, null);
    }

    public boolean updateColumn(long id, String name, String number, String memo, String createdat){
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.NAME, name);
        values.put(DataBases.CreateDB.NUMBER, number);
        values.put(DataBases.CreateDB.MEMO, memo);
        values.put(DataBases.CreateDB.CREATEDAT, createdat);
        return mDB.update(DataBases.CreateDB._TABLENAME0, values, "_id="+id, null) > 0;
    }


    public boolean increaseCount(long id, String count){
        ContentValues values = new ContentValues();

        values.put(DataBases.CreateDB.COUNT, count);
        return mDB.update(DataBases.CreateDB._TABLENAME0, values, "_id="+id, null) > 0;
    }

    // Delete All
    public void deleteAllColumns() {
        mDB.delete(DataBases.CreateDB._TABLENAME0, null, null);
    }

    // Delete Column
//    public boolean deleteColumn(long id){
//        return mDB.delete(DataBases.CreateDB._TABLENAME0, "_id="+id, null) > 0;
//    }

    public boolean deleteColumn(long id) {
        return mDB.delete(DataBases.CreateDB._TABLENAME0, "_id="+id, null) > 0;
    }



}
