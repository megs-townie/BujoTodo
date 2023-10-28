package com.russ.compose_02

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.russ.compose_02.ui.theme.Compose_02Theme
import java.util.concurrent.Executors

// Kotlin Reference:  https://kotlinlang.org/docs/home.html
// Compose Reference: https://developer.android.com/jetpack/compose/documentation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        do_work_in_background()  // Launch background DB Access

        // SetContent is compose call to assemble content ("compose" content)
        // Mouse-over setContent, Compose_01Theme, Surface, etc, to see
        // JavaDocs comments on what these are and what they do.
        setContent {
            // Compose_01Theme is declared in ui.theme
            Compose_02Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Now we run our own functions declared below, which will print
                    // to the screen using colour/surface/material per "Surface"
                    Greeting("Android")
                    Log.d(TAG, "onCreate() called")
                }
            }
        }
    }

    ///////////////////////////////// Access DB in Background //////////////////////////

    // method does DB writing work in background
    private fun do_work_in_background() {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute(object : Runnable {
            override fun run() {
                var result = ""

                //Background work here
                val DBtest = DBClass(this@MainActivity)

                // Add a row....
                run {

                    // Gets the data repository in write mode
                    val db = DBtest.writableDatabase

                    // Create a new map of values, where column names are the keys
                    val values = ContentValues()
                    values.put("str_col", "TRUCK")
                    values.put("num_col", 600)

                    // Insert the new row, returning the primary key value of the new row
                    val newRowId: Long
                    newRowId = db.insert("sample_table", null, values)
                }

                //dump all rows
                run {
                    val db = DBtest.readableDatabase

                    // Define a projection that specifies which columns from the database
                    // you will actually use after this query.
                    val projection =
                        arrayOf("id", "str_col", "num_col")
                    val selection = "num_col < ?" // ? gets filled in by args
                    val selectionArgs =
                        arrayOf("850")

                    // How you want the results sorted in the resulting Cursor
                    val sortOrder = "id" + " DESC" // sort by descending id number
                    val c = db.query(
                        "sample_table",  // The table to query
                        projection,  // The columns to return
                        selection,  // The columns for the WHERE clause
                        selectionArgs,  // The values for the WHERE clause
                        null,  // don't group the rows
                        null,  // don't filter by row groups
                        sortOrder // The sort order
                    )
                    c.moveToFirst()
                    val itemId = c.getLong(c.getColumnIndexOrThrow("id"))
                    val o: Any? = null //object returned from column in DB
                    var key = ""
                    var value = ""
                    while (c.moveToNext()) {
                        val colCount = c.columnCount
                        for (i in 0 until colCount) {
                            when (c.getType(i)) {
                                Cursor.FIELD_TYPE_INTEGER -> {
                                    key = c.getColumnName(i)
                                    value = c.getInt(i).toString()
                                }

                                Cursor.FIELD_TYPE_STRING -> {
                                    key = c.getColumnName(i)
                                    value = c.getString(i)
                                }
                            }
                            Log.d("Save_v03", "key=$key value=$value")
                            result += "key=$key value=$value\n"
                        }
                        Log.d("Save_v03", "Next Row")
                        result += "\n"
                    }
                }
                Log.d("Save_v03", "Sleep ..........................")
                try {
                    Thread.sleep(5000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }


                // in case you want to show something on UI
                val finalResult = result
                handler.post { //UI Thread work here
                    // post result
                    setContent {
                        // Compose_01Theme is declared in ui.theme
                        Compose_02Theme {
                            // A surface container using the 'background' color from the theme
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                // Now we run our own functions declared below, which will print
                                // to the screen using colour/surface/material per "Surface"
                                Greeting(finalResult)
                                Log.d(TAG, "handler.post called")
                            }
                        }
                    }
                }
                Log.d("Save_v03", "Background result...$result")
            }
        })
    }


}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    // Column allows putting text in a column (i.e. on the next row down)
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
//        // Add another column
//        Text(text = "2nd line")
//
//        // Function to add more columns
//        Greeting2()
//
//        var messages = arrayOf("Russ", "Was", "Here")
//        Greeting3(messages)

    }
    Log.d(TAG, "Greeting() called")
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Compose_02Theme {
        Greeting("Android")
    }
    Log.d(TAG, "GreetingPreview() called")
}

/////////////// Add more lines
@Composable
fun Greeting2() {
    Column {
        Text(text = "Greeting2 - line 1 - ")
        Text(text = "Greeting2 - line 2")
        Text(text = "Greeting2 - line 3")
    }
    Log.d(TAG, "Greeting() called")
}

////////////// Add from an Array
@Composable
fun Greeting3(things: Array<String>) {
    Column {
        Text(text = "  --------- Start -----------")
        for (thing in things) {
            Text(thing)
        }
        Text(text = "  --------- Done -----------")
    }
    Log.d(TAG, "Greeting() called")
}

