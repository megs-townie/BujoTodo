package com.example.m03_bounce3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

// Found tutorial to do put buttons over view  here:
// https://code.tutsplus.com/tutorials/android-sdk-creating-custom-views--mobile-14548
//
// then added JSON fetch of data:
//
// 1) Manifest line added:
//    <uses-permission android:name="android.permission.INTERNET"></uses-permission>
//
// 2) getWebServiceData added:
//      ...which uses it's own thread to request and wait for the JSON data
//
// 3) button press from main activity launches a JSON request.
//
// 4) latest versions of Android don't like clear text JSON
//        https://stackoverflow.com/questions/45940861/android-8-cleartext-http-traffic-not-permitted
//

public class MainActivity extends AppCompatActivity {

    getWebServiceData restData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // IP address of web service.
        // doesn't allow http?  needs to be https.
        restData = new getWebServiceData("https://api.chucknorris.io/jokes/random");
        //restData = new getWebServiceData("http://172.27.208.1:8081/");
        restData.start();

    }


    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {

        // log what we are doing
        Log.w("sendMessage", "No Data " );

        restData.getJSONdata();

    }
}