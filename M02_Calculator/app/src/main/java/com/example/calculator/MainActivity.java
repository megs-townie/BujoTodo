package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityGestureEvent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action when "Add" button is pressed
        Button button = (Button) findViewById(R.id.b_Add);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("M01_Calculator ADD BUTTON", "User tapped the Add button");

                Double d1 = 0.0;
                Double d2 = 0.0;
                Double answer = 0.0;

                EditText textN1 = (EditText) findViewById(R.id.editTextN1);
                EditText textN2 = (EditText) findViewById(R.id.editTextN2);
                // we actually don't need to get ans from screen
                EditText textANS = (EditText) findViewById(R.id.editTextNumAns);

                try {
                    d1 = Double.parseDouble(textN1.getText().toString());
                    d2 = Double.parseDouble(textN2.getText().toString());
                    answer = d1 + d2;
                }
                catch (Exception e) {
                    Log.w("M01_Calculator ADD BUTTON", "Add Selected with no inputs ... " + answer);
                }

                // Set the Answer into the the answer field
                textANS.setText(answer.toString());

                // log what we are doing
                Log.w("M01_Calculator ADD BUTTON", "Add Selected with => " + d1 + " + " + d2 + "=" + answer);
            }
        });

    }

}
