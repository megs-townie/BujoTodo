package com.codelab.basics;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.concurrent.Executor;


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


    // Return 2D String array of the records suitable to display
    // master-detail type list data
    public String[][] get2DRecords() {
        // Real code would select * from DB table and populate
        // the first string with a title, and the 2nd string
        // with details which could be a concat of remaining
        // fields

        Log.d("DBClass.get2DRecords", "Start===========================");
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {"id", "str_col", "num_col"};
        String selection = "num_col < ?";  // ? gets filled in by args
        String[] selectionArgs = {"850"};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = "id" + " DESC";   // sort by descending id number

        Cursor c = db.query(
                "sample_table",  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        // Returned Array is size of ResultSet
        Log.d("DBClass.get2DRecords", "c.getCount()=" + c.getCount());
        String[][] newArray = new String[c.getCount()][2];

        c.moveToFirst();
        long itemId = c.getLong(c.getColumnIndexOrThrow("id"));

        String key = "";
        String value = "";
        String keeperKey = "";

        do {
            int pos = c.getPosition();
            Log.d("DBClass.get2DRecords", "pos=" + pos);

            newArray[pos][0] = "";
            newArray[pos][1] = "";          // init so we can append later
            int colCount = c.getColumnCount();
            for (int i = 0; i < colCount; ++i) {
                switch (c.getType(i)) {
                    case Cursor.FIELD_TYPE_INTEGER:
                        key = c.getColumnName(i);
                        value = String.valueOf(c.getInt(i));
                        break;
                    case Cursor.FIELD_TYPE_STRING:
                        key = c.getColumnName(i);
                        value = c.getString(i);
                        break;
                }
                Log.d("DBClass.get2DRecords", "c.getPosition()=pos=" + pos);
                Log.d("DBClass.get2DRecords", "key=" + key + " value=" + value);
                newArray[pos][0] += key;
                newArray[pos][1] += value;
                keeperKey = c.getString(1);
            }
            // Uncomment next line, key is better?
            newArray[pos][0] = keeperKey;    // Key is name of record
            Log.d("DBClass.get2DRecords", "Next Row");
        } while (c.moveToNext());  // Do while there is a next

        Log.d("DBClass.get2DRecords", "Dump array");
        for (String[] i : newArray) {
            for (String j : i) {
                Log.d("DBClass.get2DRecords", "j=>" + j);
            }
        }

        Log.d("DBClass.get2DRecords", "Sleep ..........................");

//        // Slow down just for fun to see what happens
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        Log.d("DBClass.get2DRecords", "End  ===========================");
        return newArray;
    }


    // Return 2D String array of the records suitable to display
    // master-detail type list data ... DEBUG version
    public String[][] default_get2DRecords(){
        String[][] newArray = new String[2][2];
        newArray[0][0] = "Default";
        newArray[0][1] = "Data";
        newArray[1][0] = "Default2";
        newArray[1][1] = "Data2";
        return newArray;
    }
}
