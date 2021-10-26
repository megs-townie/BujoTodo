package com.example.m03_bounce3;

import android.graphics.Color;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;


/**
 * Created by Russ on 2/28/2016.
 */
public class getWebServiceData extends Thread {

    String content = null;
    String dataUrl = "http:\\localhost:8081";

    private boolean getNow = false;

    // delegate setup to return ball to caller
    public getResponse delegate = null;
    Ball b = null;

    public getWebServiceData(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    // let caller initiate JSON call
    // by letting the thread out.
    public void getJSONdata() {
        synchronized (this) {
            this.getNow = true;
            notifyAll();  // tell thread to go!
        }
    }

    @Override
    public void run() {

        // loop getting Web Service data, but wait on call to synch
        while (true) {

            // synch wait for boolean to give go-ahead
            synchronized (this) {
                while (!this.getNow) {
                    try {
                        wait();    // Wait to be told to go.
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            // reset boolean back so we loop once
            this.getNow = false;

            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                //url = new URL("http://localhost:8081/Greeting/");
                url = new URL(this.dataUrl);
                urlConnection = (HttpURLConnection) url.openConnection();

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                content = br.readLine();  // get the JSON string in one read

                // Show the message:
                Log.w("sendMessage", "WebService content: " + content);

                conn.disconnect();

                /////////////////////////////////////////
                // We could unpack the JSON data and add
                // a ball accordingly, if ball data was sent.
                //
                // Instead, just make a random ball to show we
                // got here.
                BouncingBallView.addBall();  //static call, so OOP could be better

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

