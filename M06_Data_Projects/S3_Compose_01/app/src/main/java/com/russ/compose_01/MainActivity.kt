package com.russ.compose_01

import android.content.ContentValues.TAG
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.russ.compose_01.ui.theme.Compose_01Theme // Specifically imported
import android.util.Log // Russ added logs
import androidx.compose.foundation.layout.Column // allows multiple rows of text

// Kotlin Reference:  https://kotlinlang.org/docs/home.html
// Compose Reference: https://developer.android.com/jetpack/compose/documentation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // SetContent is compose call to assemble content ("compose" content)
        // Mouse-over setContent, Compose_01Theme, Surface, etc, to see
        // JavaDocs comments on what these are and what they do.
        setContent {
            // Compose_01Theme is declared in ui.theme
            Compose_01Theme {
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
                /////// finished Surface
            }
            /////////// finished Compose_01Theme
        }
        /////////////// finished setContent
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
    Compose_01Theme {
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
