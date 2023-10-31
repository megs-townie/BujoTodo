package com.example.save_v03a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Using SQLite DB:
 *  => http://developer.android.com/training/basics/data-storage/databases.html
 *
 * Using AsyncTask:
 * => http://developer.android.com/guide/components/processes-and-threads.html
 *
 * This project demonstrates the structure of using the Android SQLite DB with an
 * AsyncTask.  Android does not permit long tasks on the UI thread, so AsyncTask is
 * used to perform DB actions in the background, and access UI thread in the
 * foreground for updating the UI.
 *
 */
public class MainActivity extends AppCompatActivity {

    EditText etResponse;
    TextView tvIsConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get reference to the views
        etResponse = (EditText) findViewById(R.id.etResponse);
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);

        // check if you are connected or not
        tvIsConnected.setBackgroundColor(0xFF00CC00);
        tvIsConnected.setText("DB connected");

        // call to perform network operation on separate thread
        do_work_in_background();
        Log.d("Save_v03", "Main onCreate() complete");
    }

    // method does DB writing work in background
    private void do_work_in_background() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                String result = "";

                //Background work here
                DBClass DBtest = new DBClass(MainActivity.this);

                // Add a row....
                {
                    // Gets the data repository in write mode
                    SQLiteDatabase db = DBtest.getWritableDatabase();

                    // Create a new map of values, where column names are the keys
                    ContentValues values = new ContentValues();
                    values.put("str_col", "TRUCK");
                    values.put("num_col", 600);

                    // Insert the new row, returning the primary key value of the new row
                    long newRowId;
                    newRowId = db.insert("sample_table", null, values);
                }

                //dump all rows
                {
                    SQLiteDatabase db = DBtest.getReadableDatabase();

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

                    c.moveToFirst();
                    long itemId = c.getLong(c.getColumnIndexOrThrow("id"));

                    Object o = null;  //object returned from column in DB
                    String key = "";
                    String value = "";

                    do {
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
                            Log.d("Save_v03", "key=" + key + " value=" + value);
                            result += "key=" + key + " value=" + value + "\n";
                        }
                        Log.d("Save_v03", "Next Row");
                        result += "\n";
                    } while (c.moveToNext());
                }

                Log.d("Save_v03", "Sleep ..........................");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                // in case you want to show something on UI
                String finalResult = result;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI Thread work here
                        etResponse.setText("DONE!\n\n" + finalResult);
                    }
                });

                Log.d("Save_v03", "Background result..." + result);
            }
        });

    }

}
