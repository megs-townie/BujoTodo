package com.example.russ.save_v03;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        // call AsynTask to perform network operation on separate thread
        new DB_Task().execute("blank message");

    }

    private class DB_Task extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... msg) {

            String result = "";

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

                for (; c.moveToNext(); ) {
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
                }
            }

            Log.d("Save_v03", "Sleep ..........................");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return result;

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "DONE", Toast.LENGTH_LONG).show();
            etResponse.setText(result);
            Log.d("Save_v03", result);
        }
    }

}
