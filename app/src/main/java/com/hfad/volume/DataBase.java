package com.hfad.volume;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//local SQLite database
public class DataBase extends SQLiteOpenHelper {

    private static final String DB_NAME="PhoneNumbers";
    private static final String TABLE_NAME="NumberBooK";
    private static final String COLUMN_1="MyNumber";
    private static final String COLUMN_2="Password";

    public DataBase(Context context) {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(MyNumber text,Password text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists "+ TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    //insert data in SQLite database
    public boolean insertData(String myNumber,String Password)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_1,myNumber);
        contentValues.put(COLUMN_2,Password);
        long result=db.insert(TABLE_NAME,null,contentValues);
        return result != -1;
    }

    //get data from SQLite database
    public Cursor getData()
    {
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }

}

