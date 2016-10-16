package com.myapp.ahm.recyclerview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hp on 13-02-2016.
 */
public class DBopenhelper extends SQLiteOpenHelper {
    private static final String TAG = "DBopenHelper";
   // private static final String DATABASE_NAME = "mo";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_QUERY1 =
            "CREATE TABLE "+ DBstructure.monitor.TABLE_NAME+"("+
                    DBstructure.monitor.challenge+" INTEGER,"+
                    DBstructure.monitor.status+" TEXT);";

    private static final String CREATE_QUERY2 =
            "CREATE TABLE "+ DBstructure.iteration.TABLE_NAME+"("+
                    DBstructure.iteration.number+" INTEGER);";

    private static final String CREATE_QUERY3 =
            "CREATE TABLE "+ DBstructure.timer.TABLE_NAME+"("+
                    DBstructure.timer.min+" INTEGER,"+
                    DBstructure.timer.sec+" INTEGER,"+
                    DBstructure.timer.mili+" INTEGER);";

    public DBopenhelper(Context context) {
        super(context, DBstructure.monitor.DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "creating databases...");
        db.execSQL(CREATE_QUERY1);
        db.execSQL(CREATE_QUERY2);
        db.execSQL(CREATE_QUERY3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void put_info(DBopenhelper dop,Integer ch_no,String status){
        SQLiteDatabase sq = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBstructure.monitor.challenge, ch_no);
        contentValues.put(DBstructure.monitor.status, status);
        long k = sq.insert(DBstructure.monitor.TABLE_NAME, null, contentValues);
        Log.e(TAG, "One row inserted in table monitor");
        sq.close();
    }
    public Cursor get_info(DBopenhelper dop){
        Log.e(TAG, "Getting challenge_no and status");
        SQLiteDatabase sq = dop.getReadableDatabase();
        String[] columns = {DBstructure.monitor.challenge,DBstructure.monitor.status};
        Cursor cr = sq.query(DBstructure.monitor.TABLE_NAME, columns, null, null, null, null, null);
        return cr;
    }

    public void put_info2(DBopenhelper dop,Integer no){
        SQLiteDatabase sq = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBstructure.iteration.number,no);
        long k = sq.insert(DBstructure.iteration.TABLE_NAME, null, contentValues);
        Log.e(TAG,"One row inserted in table iteration");
    }

    public Cursor get_info2(DBopenhelper dop){
        Log.e(TAG, "Getting iteration number");
        SQLiteDatabase sq = dop.getReadableDatabase();
        String[] columns = {DBstructure.iteration.number};
        Cursor cr = sq.query(DBstructure.iteration.TABLE_NAME, columns, null, null, null, null, null);
        Log.e(TAG,"got it");
        return cr;
    }

    public void put_info3(DBopenhelper dop,int min,int sec,int mm){
        SQLiteDatabase sq = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBstructure.timer.min,min);
        contentValues.put(DBstructure.timer.sec, sec);
        contentValues.put(DBstructure.timer.mili,mm);
        long k = sq.insert(DBstructure.timer.TABLE_NAME, null, contentValues);
        Log.e(TAG,"One row inserted in table iteration");
    }

    public Cursor get_info3(DBopenhelper dop){
        Log.e(TAG, "Getting timer info");
        SQLiteDatabase sq = dop.getReadableDatabase();
        String[] columns = {DBstructure.timer.min,DBstructure.timer.sec,DBstructure.timer.mili};
        Cursor cr = sq.query(DBstructure.timer.TABLE_NAME, columns, null, null, null, null, null);
        return cr;
    }

    public void update_monitor(DBopenhelper dop,Integer ch_no){
        Log.e(TAG,"in update monitor");
        SQLiteDatabase sq = dop.getWritableDatabase();
        String selection = DBstructure.monitor.challenge+" = ?";
        String args[] = {String.valueOf(ch_no)};
        ContentValues values = new ContentValues();
        values.put(DBstructure.monitor.status,"true");
        sq.update(DBstructure.monitor.TABLE_NAME, values, selection, args);
        Log.e(TAG, "finished updating monitor");
    }

    public void update_iteration(DBopenhelper dop,Integer no){
        Log.e(TAG,"in update iteration");
        SQLiteDatabase sq = dop.getWritableDatabase();
        String selection = DBstructure.iteration.number+" =?";
        String args[] = {String.valueOf(no)};
        ContentValues values = new ContentValues();
        values.put(DBstructure.iteration.number,no+1);
        sq.update(DBstructure.iteration.TABLE_NAME, values, selection, args);
        Log.e(TAG, "finished updating iteration");
    }
    public void delete_monitor(DBopenhelper dop){
        SQLiteDatabase sq = dop.getWritableDatabase();
        sq.execSQL("DELETE FROM " + DBstructure.monitor.TABLE_NAME);
        sq.close();
    }

    public void delete_iteration(DBopenhelper dop){
        SQLiteDatabase sq = dop.getWritableDatabase();
        sq.execSQL("DELETE FROM " + DBstructure.iteration.TABLE_NAME);
        sq.close();
    }

    public void delete_timer(DBopenhelper dop){
        SQLiteDatabase sq = dop.getWritableDatabase();
        sq.execSQL("DELETE FROM " + DBstructure.timer.TABLE_NAME);
        sq.close();
    }

}
