package com.example.databaseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class DHelper extends SQLiteOpenHelper {
    public static String DB_Name="student_database";
    public static String Table_Name="student";
    public static final String col1="id";
    public static final String col2="name";
    public static final String col3="marks";
    private Context context;
    public static final String table_create ="CREATE TABLE student ( id INTEGER PRIMARY KEY AUTOINCREMENT , name TEXT, marks INT(2))";
    public DHelper(@Nullable Context context) {
        super(context, DB_Name, null, 3);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Toast.makeText(context,"In onCreate()",Toast.LENGTH_LONG).show();
    try
    {
        db.execSQL(table_create);
    }
    catch(Exception e)
    {
        Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
    }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Toast.makeText(context,"In onUpgarde()",Toast.LENGTH_LONG).show();
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Table_Name);
    onCreate(sqLiteDatabase);
    }
    public boolean insertData(String name,String marks)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(col2,name);
        cv.put(col3,marks);
        long result=db.insert(Table_Name,null,cv);
        if(result==-1)
            return false;
        else
            return true;
    }
    public boolean updateData(String id, String name,String marks)
    {
       SQLiteDatabase db = getWritableDatabase();
       ContentValues cv = new ContentValues();
       cv.put(col1,id);
       cv.put(col2,name);
       cv.put(col3,marks);
       db.update(Table_Name,cv,"id=?",new String[]{id});
        return true;
    }
    public Cursor getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+Table_Name,null);
        return cur;
    }
}
