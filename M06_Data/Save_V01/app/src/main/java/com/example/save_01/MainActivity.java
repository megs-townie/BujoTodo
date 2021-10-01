package com.example.save_01;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Map;

/**
 * Activity to demonstrate Saving Data:
 * 1 - Bundle use - just for change in orientation (key/value pairs)
 * 2 - Preference File use - one default file for application (key/value pairs)
 * not shown here - getSharedPreferences() — Use this if you need multiple shared preference files identified by name
 * 3 - Internal File use - use I/O streams to read/write whatever you want
 * 4 - Serialized Object File - use ObjectInputStream/ObjectOutputStream
 * 5 - Activity lifecycle demonstrated (log at each callback)
 * 6 - follow-up with browsing files created here (use ADM -> File Explorer)
 */

public class MainActivity extends AppCompatActivity {

    // Local data Object
    myDataClass myDataObject = new myDataClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        // Read from internal File
        String filename2 = "myObject";
        FileInputStream inputStream2;
        ObjectInputStream is;
        int guard = 0;  // don't let the do-while go forever
        try {
            inputStream2 = openFileInput(filename2);
            is = new ObjectInputStream(inputStream2);
            Object o = null;
            do {
                try {
                    guard++;
                    o = (Object) (is.readObject());
                    if (o != null) {  // only set if not null
                        myDataObject = (myDataClass) (o);
                        Log.v("SAVED STATE", "onCreate Object Read="
                                + myDataObject.getMySavedString() + " "
                                + myDataObject.getMySavedInteger());
                    }
                } catch (Exception e) {
                    Log.v("SAVED STATE", "onCreate Object Stream Read Exception (no more Object?)");
                    e.printStackTrace();
                }
            } while (o != null && guard < 10);   // EOF is null as last Object

            is.close();               // close both streams in order
            inputStream2.close();

        } catch (Exception e) {
            Log.v("SAVED STATE", "onCreate Object Stream Read Exception (no Object?)");
            e.printStackTrace();
        } finally {
            Log.v("SAVED STATE", "onCreate Object Read Done");
        }


        // Read Single preferences file
        SharedPreferences sharedPref = getPreferences(this.MODE_PRIVATE);
        Map<String, ?> myMap = sharedPref.getAll();
        Log.v("SAVED STATE", "onCreate myMap=" + myMap.toString());
        Iterator entries = myMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry thisEntry = (Map.Entry) entries.next();
            Object key = thisEntry.getKey();
            Object value = thisEntry.getValue();
            Log.v("SAVED STATE", "onCreate sharedPref=" + key.toString() + ": " + value.toString());
        }


        // Read from Bundle....but Bundle is empty at very start
        String myState = "onCreate";
        String myState2, myState3, myState4;
        myState2 = myState3 = myState4 = "";
        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore values from saved state
            myState = savedInstanceState.getString("RussState1");
            myState2 = savedInstanceState.getString("RussState2");
            myState3 = savedInstanceState.getString("RussState3");
            myState4 = savedInstanceState.getString("RussState4");

        } else {
            // First run ever (on this device) create the State...
            myState = "onCreate...first ever!";
            // cannot save state into the Bundle inside onCreate()
        }

        Log.v("SAVED STATE", "onCreate State=" + myState);
        Log.v("SAVED STATE", "onCreate State2=" + myState2);
        Log.v("SAVED STATE", "onCreate State3=" + myState3);
        Log.v("SAVED STATE", "onCreate State4=" + myState4);





    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("SAVED STATE", "onStart");
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.v("SAVED STATE", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("SAVED STATE", "onPause - begin");

        // Save string
        String myState = "State Saved";

        // Save to single preference file
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("RussPref_01", myState);
        editor.putString("RussPref_02", "Hello from Russ");
        editor.commit();

        // Save to Internal File
        String filename = "myfile";
        String string = "Hello from Russ";
        FileOutputStream outputStream;
        try {
            // FileOutputStream pretty much re-writes entire file
            // If you need to random access, or append, maybe consider DB access
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write("...write this...".getBytes());
            outputStream.write(System.lineSeparator().getBytes());
            outputStream.write(string.getBytes());
            outputStream.write(System.lineSeparator().getBytes());
            outputStream.write("...write more...".getBytes());
            outputStream.write("...stop writing!".getBytes());
            outputStream.flush();  // ensure data is written
            // Use ADM to find \data\data\<my-apps-full-name\files directory
            Log.v("SAVED STATE", "onPause File Path=" + Environment.getDataDirectory().getAbsolutePath());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Save to Object File
        String filename2 = "myObject";
        myDataObject.setMySavedInteger(myDataObject.getMySavedInteger() + 1);
        myDataObject.setMySavedString(myDataObject.getMySavedString() + "X");
        FileOutputStream outputStream2;
        try {
            outputStream2 = openFileOutput(filename2, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(outputStream2);
            os.writeObject(myDataObject);
            os.writeObject(null);
            os.flush();  // ensure data is written
            // Use ADM to find \data\data\<my-apps-full-name\files directory
            Log.v("SAVED STATE", "onPause Object Path=" + Environment.getDataDirectory().getAbsolutePath());
            os.close();
            outputStream2.close();      // close both streams in order
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.v("SAVED STATE", "onPause - end");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("SAVED STATE", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("SAVED STATE", "onDestroy");
    }

    // onRestoreInstanceState pretty much only called changing orientation (ctrl-f12)
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Read Bundle
        String myState = "OnRestoreInstanceState";
        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore values from saved state
            myState = savedInstanceState.getString("RussState");
        } else {
            // First run ever (on this device) create the State...
            savedInstanceState.putString("RussState", "onRestoreInstanceState No State Saved Yet");
        }

        Log.v("SAVED STATE", "OnRestoreInstanceState State=" + myState);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

        Log.v("SAVED STATE", "onSaveInstanceState 1");

        // Save to the Bundle
        String myState = "State Saved";
        savedInstanceState.putString("RussState1", myState);
        savedInstanceState.putString("RussState2", myState);
        savedInstanceState.putString("RussState3", "Woops....this one is different");

        Log.v("SAVED STATE", "onSaveInstanceState 2 State=" + myState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.v("SAVED STATE", "onCreateOptionsMenu");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.v("SAVED STATE", "onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
