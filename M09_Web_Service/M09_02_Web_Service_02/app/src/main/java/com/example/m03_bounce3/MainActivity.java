package com.example.m03_bounce3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

// Found tutorial to do put buttons over view  here:
// https://code.tutsplus.com/tutorials/android-sdk-creating-custom-views--mobile-14548

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {


        // log what we are doing
        Log.w("sendMessage", "Data =>" + "");

        // IP address of web service.
        getWebServiceData restData = new getWebServiceData("http://172.24.48.1:8081/");
        //getWebServiceData restData = new getWebServiceData("https://api.chucknorris.io/jokes/random");
        restData.start();


    }
}