package com.example.a7_sensors;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private SnakeGameView snakeGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        snakeGameView = new SnakeGameView(this);
        setContentView(snakeGameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        snakeGameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        snakeGameView.resume();
    }
}
