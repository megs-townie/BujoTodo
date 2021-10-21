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

    // delegate setup to return ball to caller
    public getResponse delegate = null;
    Ball b = null;

    public getWebServiceData(String dataUrl){
        this.dataUrl = dataUrl;
    }


    @Override
    public void run() {

        // loop getting Web Service data
        while (true) {

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
//                System.out.println("WebService content: " + content);
                Log.w("sendMessage", "WebService content: " + content);
//
//                EditText editText = (EditText) findViewById(R.id.editText);

                conn.disconnect();

                /////////////////////////////////////////
                // Make Network Animal from json string
//                try {
//                    JSONObject getAnimal = new JSONObject(content);
//                    int x = getAnimal.getInt("pos_x");
//                    int y = getAnimal.getInt("pos_y");
//                    int dx = getAnimal.getInt("vel_x");
//                    int dy = getAnimal.getInt("vel_y");
//                    System.out.println("Output vals: x=" + x + " y=" + y + " dx=" + dx + " dy=" + dy);
//
//                    b = new Ball(Color.DKGRAY, (float) x, (float) y, (float) dx, (float) dy);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // bottom of While... give response
//            delegate.getBallResponse(b);

            // Wait a few seconds...
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
