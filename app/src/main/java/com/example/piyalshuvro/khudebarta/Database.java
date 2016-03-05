package com.example.piyalshuvro.khudebarta;

/**
 * Created by Piyal Shuvro on 8/22/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Pial-PC on 8/16/2015.
 */
public class Database extends SQLiteOpenHelper {
    SQLiteDatabase database;
    Context context;
    private static final String Logtag = "Message";

    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String Database_Name = "sms_database.db";
    //  table name
    public static final String Table_Name = "sms_table";
    //table columnsDatabase_Name
    public static final String Column_ID = "_id";
    public static final String Column_NUMBER = "number";
    public static final String Column_TEXTBODY = "text_body";
    public static final String Column_TYPE = "text_type";
    public static final String Column_PASSWORD = "password";
    public static final String Column_Date = "msg_date";
    public static final String Column_Status = "msg_status";


    /*public static final String Password_Table = "password_table";
    public static final String Password_ID = "password_id";
    public static final String Password_Coulumn_Number = "password_number";
    *//*private static final String Password_Table_QUERY="CREATE TABLE "+Password_Table+"("+Password_ID+" INTEGER AUTOINCREMENT,"+Password_Coulumn_Number+" TEXT,"+")";*//*
*/

    //Database Create Query
    private static final String CREATE_QUERY = "CREATE TABLE " + Table_Name + " (" +
            Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Column_NUMBER + " TEXT, " +
            Column_TEXTBODY + " TEXT, " +
            Column_TYPE + " TEXT, " +
            Column_PASSWORD + " INTEGER, " +
            Column_Date + " TEXT, " +
            Column_Status+" TEXT);";

    public Database(Context context) {
        super(context, Database_Name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY); //Create table CSE_31
        /*db.execSQL(Password_Table_QUERY);*/
        Log.i(Logtag, "Table has been created.");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Starting the database


}

