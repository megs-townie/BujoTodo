package com.example.russ.save_v03;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by w0091766 on 4/29/2016.
 */
public class DBClass extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "TEST_DB.db";

    public DBClass(Context context) {
        super(context, "DB_Name", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("Save_v03", "DB onCreate()");

        db.execSQL("CREATE TABLE sample_table (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, str_col VARCHAR(256), num_col INTEGER)");

        db.execSQL(
                "INSERT INTO sample_table(str_col,num_col) VALUES('Ford', 100)");
        db.execSQL(
                "INSERT INTO sample_table(str_col,num_col) VALUES('Toyota', 200)");
        db.execSQL(
                "INSERT INTO sample_table(str_col,num_col) VALUES('Honda', 300)");
        db.execSQL(
                "INSERT INTO sample_table(str_col,num_col) VALUES('GM', 400)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        Log.d("Save_v03", "DB onUpgrade() to version " + DATABASE_VERSION);
        db.execSQL("DROP TABLE IF EXISTS sample_table");
        onCreate(db);
    }
}
