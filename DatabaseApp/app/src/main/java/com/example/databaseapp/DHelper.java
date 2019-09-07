package com.example.databaseapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DHelper extends SQLiteOpenHelper {
    public static String DB_Name="student_database";
    public static String Table_Name="student";
    public static final String col1="usn";
    public static final String col2="name";
    public static final String col3="marks";
    private Context context;
    public static final String table_create ="CREATE TABLE student_id (INTEGER PRIMARY KEY AUTOINCREMENT , name TEXT, marks INT(2))";
    public DHelper(@Nullable Context context) {
        super(context, DB_Name, null, 3);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
