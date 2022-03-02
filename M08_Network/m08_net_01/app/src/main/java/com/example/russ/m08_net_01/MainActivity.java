package com.example.russ.m08_net_01;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.russ.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void sendMessage(View view) {

        EditText editText = (EditText) findViewById(R.id.server_ip);
        String Server_IP = editText.getText().toString();

        View bouncingBallView = new BouncingBallView(this, Server_IP);
        setContentView(bouncingBallView);

        Log.v("BouncingBallLog", "Start with Server IP = " + Server_IP);

    }
}