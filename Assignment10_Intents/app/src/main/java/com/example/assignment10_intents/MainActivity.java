package com.example.assignment10_intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find and set click listeners for each button
        ImageButton imageButtonDA = findViewById(R.id.imageButton_DA);
        ImageButton imageButtonFirefox = findViewById(R.id.imageButton_firefox);
        ImageButton imageButtonLimewire = findViewById(R.id.imageButton_limewire);
        ImageButton imageButtonLiveJournal = findViewById(R.id.imageButton_livejournal);

        // Define click listener for each button
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String websiteUrl = null;

                // Determine the URL based on which button was clicked
                if (v == imageButtonDA) {
                    websiteUrl = "http://www.deviantart.com";
                } else if (v == imageButtonFirefox) {
                    websiteUrl = "http://www.mozilla.org";
                } else if (v == imageButtonLimewire) {
                    websiteUrl = "http://www.limewire.com";
                } else if (v == imageButtonLiveJournal) {
                    websiteUrl = "http://www.livejournal.com";
                }

                // Open the website using an Intent
                if (websiteUrl != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
                    startActivity(intent);
                }
            }
        };

        // Set the click listener for each button
        imageButtonDA.setOnClickListener(buttonClickListener);
        imageButtonFirefox.setOnClickListener(buttonClickListener);
        imageButtonLimewire.setOnClickListener(buttonClickListener);
        imageButtonLiveJournal.setOnClickListener(buttonClickListener);
    }
}

